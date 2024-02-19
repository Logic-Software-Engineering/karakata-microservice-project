package com.karakata.buyerservice.cartitem.controller;

import com.karakata.buyerservice.cartitem.dto.CartRequest;
import com.karakata.buyerservice.cartitem.dto.CartResponse;
import com.karakata.buyerservice.cartitem.dto.CartUpdate;
import com.karakata.buyerservice.cartitem.model.CartItem;
import com.karakata.buyerservice.cartitem.service.CartItemService;
import com.karakata.buyerservice.client.ProductBuyerClient;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/cart")
public record CartItemController(CartItemService cartItemService, ModelMapper modelMapper, ProductBuyerClient productBuyerClient) {

    @PostMapping("/addItemToCart")
    public ResponseEntity<String> addItemToCart(@RequestBody CartRequest cartRequest) {
        CartItem cartItem = modelMapper.map(cartRequest, CartItem.class);
        CartItem post = cartItemService.addItemToCart(cartItem);
        CartRequest posted = modelMapper.map(post, CartRequest.class);
        return new ResponseEntity<>("Item added successfully to cart", HttpStatus.OK);
    }

    @GetMapping("/findCartById")
    public ResponseEntity<CartResponse> getCartById(@RequestParam("id") Long id){
        CartItem cartItem=cartItemService.fetchCartById(id);
        CartResponse cartResponse=convertCartToDto(cartItem);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @GetMapping("/findByCartCode")
    public ResponseEntity<CartResponse> getByCartCode(@RequestParam("code") String cartCode){
        CartItem cartItem=cartItemService.fetchCartByCode(cartCode);
        CartResponse cartResponse=convertCartToDto(cartItem);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @GetMapping("/allCarts")
    public ResponseEntity<List<CartResponse>> getAllCarts(@RequestParam("pageNumber") int pageNumber,
                                                          @RequestParam("pageSize") int pageSize){
        return new ResponseEntity<>(cartItemService.fetchAllCart(pageNumber, pageSize)
                .stream().map(this::convertCartToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateCart")
    public ResponseEntity<String> updateCart(@RequestBody CartUpdate cartUpdate, @RequestParam("id") Long id){
        CartItem cartItem=modelMapper.map(cartUpdate, CartItem.class);
        CartItem post=cartItemService.editCart(cartItem,id);
        CartUpdate posted=modelMapper.map(post, CartUpdate.class);
        return new ResponseEntity<>("Cart item quantity updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<String> deleteCart(@RequestParam("id") Long id){
        cartItemService.deleteCart(id);
        return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
    }

    private CartResponse convertCartToDto(CartItem cartItem) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cartItem.getId());
        cartResponse.setCartCode(cartItem.getCartCode());
        cartResponse.setProductId(cartItem.getProductId());
        cartResponse.setOrderQuantity(cartItem.getOrderQuantity());
        cartResponse.setCartTotal(cartItem.getCartTotal());
        return cartResponse;
    }
}
