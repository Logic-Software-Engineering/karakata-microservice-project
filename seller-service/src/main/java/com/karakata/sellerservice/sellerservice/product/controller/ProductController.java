package com.karakata.sellerservice.sellerservice.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karakata.sellerservice.sellerservice.image.model.Picture;
import com.karakata.sellerservice.sellerservice.product.dto.ProductApproval;
import com.karakata.sellerservice.sellerservice.product.dto.ProductRequest;
import com.karakata.sellerservice.sellerservice.product.dto.ProductResponse;
import com.karakata.sellerservice.sellerservice.product.dto.ProductUpdate;
import com.karakata.sellerservice.sellerservice.product.model.Product;
import com.karakata.sellerservice.sellerservice.product.service.ProductService;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCategory;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/product")
public record ProductController(ProductService productService, ModelMapper modelMapper) {

    @PostMapping(value = {"/addProduct"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(@RequestPart("product") ProductRequest productRequest,
                                             @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        List<Picture> pictures = uploadPictures(multipartFiles);
        try {
            productRequest.setPictures(pictures);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Product product=modelMapper.map(productRequest, Product.class);
        productService.addProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    @PutMapping("/approveProductCreation")
    public ResponseEntity<String> approveProductCreation(@RequestBody ProductApproval productApproval) throws JsonProcessingException {
        productService.approveProductCreation(productApproval.getMakerCheckerId(),
                productApproval.getAdminId(), productApproval.getRequestStatus());
        return new ResponseEntity<>("Product creation has been approved", HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<ProductResponse> getProductById(@RequestParam("id") Long id) {
        Product product = productService.fetchProductById(id);
        ProductResponse productResponse = convertProductToDto(product);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/allProductsOrByName")
    public ResponseEntity<List<ProductResponse>> getAllProductsOrByName(@RequestParam("productName") String productName,
                                                                        @RequestParam("pageNumber") int pageNumber,
                                                                        @RequestParam("pageSize") int pageSize) {

        return new ResponseEntity<>(productService.fetchAllProductOrByName(productName, pageNumber, pageSize)
                .stream().map(this::convertProductToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/productByPriceLimit")
    public ResponseEntity<List<ProductResponse>> getProductByPriceLimit(@RequestParam("productName") String productName,
                                                                        @RequestParam("price") BigDecimal price,
                                                                        @RequestParam("pageNumber") int pageNumber,
                                                                        @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(productService.fetchProductByPriceLimit(productName, price,
                        pageNumber, pageSize).stream().map(this::convertProductToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/productByPriceRange")
    public ResponseEntity<List<ProductResponse>> getProductByPriceLimit(@RequestParam("productName") String productName,
                                                                        @RequestParam("lowerPrice") BigDecimal lowerPrice,
                                                                        @RequestParam("upperPrice") BigDecimal upperPrice,
                                                                        @RequestParam("pageNumber") int pageNumber,
                                                                        @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(productService.fetchProductBetweenPriceRange(productName, lowerPrice,
                        upperPrice, pageNumber, pageSize)
                .stream().map(this::convertProductToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/productByCategory")
    public ResponseEntity<List<ProductResponse>> getProductByCategory(@RequestParam("categoryName") ProductCategory categoryName,
                                                                      @RequestParam("pageNumber") int pageNumber,
                                                                      @RequestParam("pageSize") int pageSize) {
        return new ResponseEntity<>(productService.fetchProductByCategory(categoryName, pageNumber, pageSize)
                .stream().map(this::convertProductToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/productsBySeller")
    public ResponseEntity<List<ProductResponse>> getProductsBySeller(@RequestParam("sellerId") Long sellerId) {
        return new ResponseEntity<>(productService.fetchProductsBySeller(sellerId)
                .stream().map(this::convertProductToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/editProduct")
    public ResponseEntity<String> updateProduct(@RequestBody ProductUpdate productUpdate,
                                                @RequestParam("makerCheckerId") String makerCheckerId) throws JsonProcessingException {
        Product product = modelMapper.map(productUpdate, Product.class);
        productService.editProduct(makerCheckerId,product);
//        ProductUpdate posted = modelMapper.map(post, ProductUpdate.class);
        return new ResponseEntity<>("Product updated pending approval", HttpStatus.OK);
    }

    @PutMapping("/productUpdateApproval")
    public ResponseEntity<String> productApprovalUpdate(@RequestBody ProductApproval productApproval) throws JsonProcessingException {
        productService.approveProductUpdate(productApproval.getMakerCheckerId(), productApproval.getAdminId(),
                productApproval.getRequestStatus());
        return new ResponseEntity<>("Product update has been approved", HttpStatus.OK);
    }

    @PutMapping("/reduceProductQuantity")
    public ResponseEntity<String> reduceProductQuantity(@RequestParam("productId") Long productId,
                                                        @RequestParam("quantity") int quantity) {
        productService.reduceProductQuantity(productId, quantity);
        return new ResponseEntity<>(quantity + " quantity of product ID " + productId + " has been purchased", HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestParam("makerCheckerId") String makerCheckerId) throws JsonProcessingException {
        productService.deleteProduct(makerCheckerId);
        return new ResponseEntity<>("Product " + makerCheckerId + " awaiting approval to be deleted", HttpStatus.OK);
    }

    @PutMapping("/approveProductDeletion")
    public ResponseEntity<String> approveProductDeletion(@RequestBody ProductApproval productApproval) throws JsonProcessingException {
        productService.approveDeleteProduct(productApproval.getMakerCheckerId(), productApproval.getAdminId(),
                productApproval.getRequestStatus());
        return new ResponseEntity<>("Product deletion approval is successful", HttpStatus.OK);
    }


    private ProductResponse convertProductToDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setSku(product.getSku());
        productResponse.setProductName(product.getProductName());
        productResponse.setBrand(product.getBrand());
        productResponse.setManufacturerWebSite(product.getManufacturerWebSite());
        productResponse.setProductDescription(product.getProductDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setColours(product.getColours());
        productResponse.setPictures(product.getPictures());
        productResponse.setProductCondition(product.getProductCondition());
        productResponse.setProductCategory(product.getCategory());
        productResponse.setColours(product.getColours());
        productResponse.setUnitOfMeasure(product.getUnitOfMeasure());
        productResponse.setSellerId(product.getSellerId());
        return productResponse;
    }

    private List<Picture> uploadPictures(MultipartFile[] files) throws IOException {
        List<Picture> pictures = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            Picture picture = new Picture(multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    multipartFile.getBytes());
            pictures.add(picture);
        }
        return pictures;
    }
}
