package com.karakata.sellerservice.sellerservice.product.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karakata.sellerservice.sellerservice.client.AdminClient;
import com.karakata.sellerservice.sellerservice.makerchecker.exception.MakerCheckerNotFoundException;
import com.karakata.sellerservice.sellerservice.makerchecker.model.MakerChecker;
import com.karakata.sellerservice.sellerservice.makerchecker.repository.MakerCheckerRepository;
import com.karakata.sellerservice.sellerservice.product.client.SellerClient;
import com.karakata.sellerservice.sellerservice.product.dto.AdminResponse;
import com.karakata.sellerservice.sellerservice.product.dto.SellerResponse;
import com.karakata.sellerservice.sellerservice.product.exception.ProductNotFoundException;
import com.karakata.sellerservice.sellerservice.product.model.Product;
import com.karakata.sellerservice.sellerservice.product.repository.ProductRepository;
import com.karakata.sellerservice.sellerservice.staticdata.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerClient sellerClient;
    @Autowired
    private AdminClient adminClient;
    @Autowired
    private MakerCheckerRepository makerCheckerRepository;


    @Override
    public void addProduct(Product product) throws JsonProcessingException {
        ResponseEntity<SellerResponse> sellerResponse = sellerClient.getBySellerId(product.getSellerId());
        if (!sellerResponse.getBody().getId().equals(product.getSellerId())) {
            throw new ProductNotFoundException("Seller with ID " + product.getSellerId() + " does not exist");
        }

        product.setProductAvailability(ProductAvailability.IN_STOCK);
        product.setSku(String.valueOf(100 + new Random().nextInt(LocalDateTime.now().getNano())));
        ObjectMapper objectMapper = new ObjectMapper();

        MakerChecker makerChecker = new MakerChecker();
        makerChecker.setId(String.valueOf(new Random().nextInt(LocalDateTime.now().getNano())));
        makerChecker.setEntityType(EntityType.PRODUCT);
        makerChecker.setRequestType(RequestType.CREATE);
        makerChecker.setRequestStatus(RequestStatus.PENDING);
        makerChecker.setNewState(objectMapper.writeValueAsString(product));
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void approveProductCreation(String makerCheckerId, Long checkerId, RequestStatus requestStatus) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<AdminResponse> adminResponse = adminClient.getAdminById(checkerId);

        MakerChecker savedMakerChecker = makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(() -> new MakerCheckerNotFoundException("MakerChecker ID " + makerCheckerId + " not found"));

        Product product = objectMapper.readValue(savedMakerChecker.getNewState(), Product.class);
        log.info("Product before saving {}", product);
        savedMakerChecker.setOldState(objectMapper.writeValueAsString(product));
        log.info("Old state {}", savedMakerChecker.getOldState());
        savedMakerChecker.setRequestStatus(requestStatus);
        savedMakerChecker.setAdminId(adminResponse.getBody().getId());
        if (savedMakerChecker.getRequestStatus() == RequestStatus.APPROVED) {
            productRepository.save(product);
            savedMakerChecker.setNewState(objectMapper.writeValueAsString(product));
            savedMakerChecker.setEntityId(product.getId());
        }
        makerCheckerRepository.save(savedMakerChecker);
        log.info("New state {}", savedMakerChecker.getNewState());
    }

    @Override
    public Product fetchProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product " + id + " not found"));
    }

    @Override
    public List<Product> fetchAllProductOrByName(String productName, int pageNumber, int pageSize) {
        if (productName.equals("")) {
            return productRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
        } else return productRepository.findByProductName(productName, PageRequest.of(pageNumber, pageSize));

    }

    @Override
    public List<Product> fetchProductByPriceLimit(String productName, BigDecimal price,
                                                  int pageNumber, int pageSize) {
        return productRepository.findProductByPriceWithinPriceLimit(productName, price,
                PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<Product> fetchProductBetweenPriceRange(String productName, BigDecimal lowerPrice,
                                                       BigDecimal upperPrice, int pageNumber, int pageSize) {
        return productRepository.findProductNameBetweenPriceRange(productName, lowerPrice, upperPrice,
                PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<Product> fetchProductByCategory(ProductCategory categoryName, int pageNumber, int pageSize) {

        return productRepository.findProductByCategory(categoryName, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<Product> fetchProductsBySeller(Long sellerId) {
        return productRepository.findProductBySeller(sellerId);
    }

    @Override
    public void editProduct(String makerCheckerId, Product product) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MakerChecker makerChecker = makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(() -> new MakerCheckerNotFoundException("Maker Checker ID " + makerCheckerId + " not found"));
        Product savedProduct = productRepository.findById(makerChecker.getEntityId())
                .orElseThrow(() -> new ProductNotFoundException("Product ID " + makerChecker.getEntityId() + " not found"));

        makerChecker.setOldState(objectMapper.writeValueAsString(savedProduct));
        makerChecker.setRequestStatus(RequestStatus.PENDING);
        makerChecker.setRequestType(RequestType.UPDATE);
        makerChecker.setEntityType(makerChecker.getEntityType());
        savedProduct.setQuantity(product.getQuantity());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setProductDescription(product.getProductDescription());
        savedProduct.setProductName(product.getProductName());
        savedProduct.setUnitOfMeasure(product.getUnitOfMeasure());
        savedProduct.setPictures(product.getPictures());
        savedProduct.setCategory(product.getCategory());
        makerChecker.setNewState(objectMapper.writeValueAsString(savedProduct));
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void approveProductUpdate(String makerCheckerId, Long adminId, RequestStatus requestStatus) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<AdminResponse> adminResponse = adminClient.getAdminById(adminId);
        MakerChecker makerChecker = makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(() -> new MakerCheckerNotFoundException("MakerChecker ID " + makerCheckerId + " not found"));
        makerChecker.setRequestStatus(requestStatus);
        Product product = objectMapper.readValue(makerChecker.getNewState(), Product.class);
        makerChecker.setOldState(objectMapper.writeValueAsString(product));
        if (makerChecker.getRequestStatus() == RequestStatus.APPROVED) {
            productRepository.save(product);
            makerChecker.setNewState(objectMapper.writeValueAsString(product));
            makerChecker.setEntityId(product.getId());
            makerChecker.setAdminId(adminResponse.getBody().getId());
        }
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void reduceProductQuantity(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product ID " + productId + " not found"));
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(String makerCheckerId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MakerChecker makerChecker = makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(() -> new MakerCheckerNotFoundException("MakerChecker ID " + makerCheckerId + " not found"));
        Product product = productRepository.findById(makerChecker.getEntityId())
                .orElseThrow(() -> new ProductNotFoundException("Product ID " + makerChecker.getEntityId() + " not found"));
        makerChecker.setOldState(objectMapper.writeValueAsString(product));
        makerChecker.setRequestStatus(RequestStatus.PENDING);
        makerChecker.setRequestType(RequestType.DELETE);
        makerChecker.setEntityType(EntityType.PRODUCT);
        makerCheckerRepository.save(makerChecker);
    }

    @Override
    public void approveDeleteProduct(String makerCheckerId, Long adminId, RequestStatus requestStatus) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MakerChecker makerChecker = makerCheckerRepository.findById(makerCheckerId)
                .orElseThrow(() -> new MakerCheckerNotFoundException("MakerChecker ID " + makerCheckerId + " not found"));
        makerChecker.setRequestStatus(requestStatus);
        ResponseEntity<AdminResponse> adminResponse = adminClient.getAdminById(adminId);
        Product product = objectMapper.readValue(makerChecker.getOldState(), Product.class);
        makerChecker.setEntityId(product.getId());
        makerChecker.setAdminId(adminResponse.getBody().getId());
        if (makerChecker.getRequestStatus() == RequestStatus.APPROVED) {
            productRepository.deleteById(product.getId());
            makerChecker.setNewState(objectMapper.writeValueAsString(product));//test
        }
        makerCheckerRepository.save(makerChecker);
    }
}
