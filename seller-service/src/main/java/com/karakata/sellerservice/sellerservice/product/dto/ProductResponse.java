package com.karakata.sellerservice.sellerservice.product.dto;

import com.karakata.sellerservice.sellerservice.colour.model.Colour;
import com.karakata.sellerservice.sellerservice.image.model.Picture;
import com.karakata.sellerservice.sellerservice.staticdata.ProductAvailability;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCategory;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCondition;
import com.karakata.sellerservice.sellerservice.staticdata.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String sku;
    private String productName;
    private String brand;
    private String manufacturerWebSite;
    private String productDescription;
    private ProductAvailability productAvailability;
    private BigDecimal price;
    private Integer quantity;
    private ProductCondition productCondition;
    private UnitOfMeasure unitOfMeasure;
    private ProductCategory productCategory;
    private List<Colour> colours=new ArrayList<>();
    private List<Picture> pictures = new ArrayList<>();
    private Long sellerId;
}
