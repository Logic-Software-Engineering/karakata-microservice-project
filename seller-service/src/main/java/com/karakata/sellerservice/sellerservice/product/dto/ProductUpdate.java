package com.karakata.sellerservice.sellerservice.product.dto;


import com.karakata.sellerservice.sellerservice.colour.model.Colour;
import com.karakata.sellerservice.sellerservice.image.model.Picture;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCategory;
import com.karakata.sellerservice.sellerservice.staticdata.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdate {
    private Long id;
    private String productName;
    private String productDescription;
    private ProductCategory productCategory;
    private BigDecimal price;
    private Integer quantity;
    private UnitOfMeasure unitOfMeasure;
    private List<Colour> colours=new ArrayList<>();
    private List<Picture> pictures=new ArrayList<>();
}
