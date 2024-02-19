package com.karakata.sellerservice.sellerservice.product.model;

import com.karakata.sellerservice.sellerservice.colour.model.Colour;
import com.karakata.sellerservice.sellerservice.image.model.Picture;
import com.karakata.sellerservice.sellerservice.staticdata.ProductAvailability;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCategory;
import com.karakata.sellerservice.sellerservice.staticdata.ProductCondition;
import com.karakata.sellerservice.sellerservice.staticdata.UnitOfMeasure;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Builder
public class Product implements Serializable {
    @Id
    @SequenceGenerator(name = "product_seq",
            sequenceName = "product_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "product_gen")
    private Long id;
    private String sku;

    @Column(nullable = false)
    private String productName;
    private String brand;
    private String manufacturerWebSite;

    @NotNull
    private String productDescription;
    private boolean deleted;

    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @Enumerated(EnumType.STRING)
    private ProductCondition productCondition;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductAvailability productAvailability;


    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Colour> colours = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Picture> pictures = new ArrayList<>();

    @Column(nullable = false, name = "maker_id")
    private Long sellerId;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date updatedAt;
}
