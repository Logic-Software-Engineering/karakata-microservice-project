package com.karakata.buyerservice.order.service;

import com.karakata.buyerservice.cartitem.dto.ProductResponse;
import com.karakata.buyerservice.cartitem.exception.CartItemExceptionNotFound;
import com.karakata.buyerservice.cartitem.model.CartItem;
import com.karakata.buyerservice.cartitem.service.CartItemService;
import com.karakata.buyerservice.client.ProductBuyerClient;
import com.karakata.buyerservice.client.UserClient;
import com.karakata.buyerservice.invoice.repository.InvoiceRepository;
import com.karakata.buyerservice.order.dto.UserResponse;
import com.karakata.buyerservice.order.exception.OrderNotFoundException;
import com.karakata.buyerservice.order.model.Order;
import com.karakata.buyerservice.order.repository.OrderRepository;
import com.karakata.buyerservice.staticdata.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductBuyerClient productBuyerClient;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Order placeOrder(Order order) {
        ResponseEntity<UserResponse> userResponse = userClient.getUserById(order.getUserId());

        if (!order.getUserId().equals(userResponse.getBody().getId())) {
            throw new OrderNotFoundException(order.getUserId() + " is not a registered user");
        }

        for (CartItem cartItem : order.getCartItems()) {
            ResponseEntity<ProductResponse> productResponse = productBuyerClient.getProductById(cartItem.getProductId());
            if (!productResponse.getBody().getId().equals(cartItem.getProductId())) {
                throw new OrderNotFoundException("Product does not exist");
            }
            if (cartItem.getOrderQuantity()<=productResponse.getBody().getQuantity()) {
                cartItem.setCartTotal(productResponse.getBody().getPrice()
                        .multiply(new BigDecimal(cartItem.getOrderQuantity())));
            } else {
                throw new CartItemExceptionNotFound("Insufficient product quantity");
            }
            productBuyerClient.reduceProductQuantity(cartItem.getProductId(), cartItem.getOrderQuantity());
//            if (productResponse.getBody().getQuantity()<1){
//                productResponse.getBody().
//            }
        }
        order.setOrderStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());
        order.setExpectedDeliveryDate(order.getCreatedAt().plusDays(5));
        order.setOrderNumber("Order-".concat(String.valueOf(1000 + new Random().nextInt(Instant.now().getNano()))));
        order.setOrderSubTotal(cartItemService.calculateCartTotal(order.getCartItems()));
        order.setOrderTotal(cartItemService.calculateCartTotal(order.getCartItems())
                .add(cartItemService.calculateCartTotal(order.getCartItems())
                        .multiply(order.getVat()).divide(new BigDecimal(100))));
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order fetchOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order ID " + id + " not found"));
    }

    @Override
    public List<Order> fetchByUser(Long userId, int pageNumber, int pageSize) {
        return orderRepository.findOrderByUserId(userId, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<Order> fetchOrderNumber(String searchKey, int pageNumber, int pageSize) {
        if (searchKey.equals("")) {
            return orderRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
        } else return orderRepository.findByOrderNumber(searchKey, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Order editOrder(Order order, Long id) {
        Order savedOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order ID " + id + " not found"));
        if (Objects.nonNull(order.getOrderStatus()) && !"".equals(order.getOrderStatus())) {
            savedOrder.setOrderStatus(order.getOrderStatus());
        }
        if (Objects.nonNull(order.getExpectedDeliveryDate()) && !"".equals(order.getExpectedDeliveryDate())) {
            savedOrder.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        }
        return orderRepository.save(savedOrder);
    }

    @Override
    public void cancelOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(()->new OrderNotFoundException("Order ID " + orderId + " not found"));
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.deleteById(orderId);
        }
    }
}
