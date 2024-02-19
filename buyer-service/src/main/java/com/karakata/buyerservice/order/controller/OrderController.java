package com.karakata.buyerservice.order.controller;

import com.karakata.buyerservice.cartitem.service.CartItemService;
import com.karakata.buyerservice.invoice.model.Invoice;
import com.karakata.buyerservice.invoice.service.InvoiceService;
import com.karakata.buyerservice.order.dto.OrderRequest;
import com.karakata.buyerservice.order.dto.OrderResponse;
import com.karakata.buyerservice.order.dto.OrderUpdate;
import com.karakata.buyerservice.order.model.Order;
import com.karakata.buyerservice.order.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/order")
public record OrderController(OrderService orderService, ModelMapper modelMapper,
                              InvoiceService invoiceService, CartItemService cartItemService) {

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        Order post = orderService.placeOrder(order);
        OrderRequest posted = modelMapper.map(post, OrderRequest.class);
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("Invoice-".concat(String.valueOf(10 + new Random().nextInt(LocalDateTime.now().getNano()))));
        invoice.setInvoiceDate(new Date());
        invoice.setOrder(post);
        invoice.setOrderTotal(order.getOrderSubTotal());
        invoice.setVat(cartItemService.calculateCartTotal(order.getCartItems())
                .multiply(invoice.getVat()).divide(new BigDecimal(100)));
        invoice.setInvoiceTotalAmount(order.getOrderSubTotal().add(invoice.getVat()));
        invoiceService.createOrderInvoice(invoice);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
    }

    @GetMapping("/orderById")
    public ResponseEntity<OrderResponse> orderById(@RequestParam("id") Long id) {
        Order order = orderService.fetchOrderById(id);
        OrderResponse orderResponse = convertOrderToDto(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/orderByCode")
    public ResponseEntity<List<OrderResponse>> orderByCode(@RequestParam("orderNumber") String code,
                                                           @RequestParam("pageNumber") int pageNumber,
                                                           @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(orderService.fetchOrderNumber(code, pageNumber, pageSize)
                .stream().map(this::convertOrderToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/userOrders")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@RequestParam("userId") Long userId,
                                                             @RequestParam("pageNumber") int pageNumber,
                                                             @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(orderService.fetchByUser(userId, pageNumber, pageSize)
                .stream().map(this::convertOrderToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateOrderStatusAndDelivery")
    public ResponseEntity<String> updateOrder(@RequestBody OrderUpdate orderUpdate, @RequestParam("id") Long id) {
        Order order = modelMapper.map(orderUpdate, Order.class);
        Order post = orderService.editOrder(order, id);
        OrderUpdate posted = modelMapper.map(post, OrderUpdate.class);
        return new ResponseEntity<>("Order status updated", HttpStatus.OK);
    }

    @DeleteMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam("id") Long id) {
        orderService.cancelOrder(id);
        return new ResponseEntity<>("Order number " + id + " cancelled successfully", HttpStatus.OK);
    }

    private OrderResponse convertOrderToDto(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setUserId(order.getUserId());
        orderResponse.setCartItems(order.getCartItems());
        orderResponse.setVat(order.getVat());
        orderResponse.setOrderSubTotal(order.getOrderSubTotal());
        orderResponse.setOrderTotal(order.getOrderTotal());
        return orderResponse;
    }
}
