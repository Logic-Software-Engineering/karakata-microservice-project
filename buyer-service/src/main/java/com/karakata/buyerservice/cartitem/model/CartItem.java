package com.karakata.buyerservice.cartitem.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE cart_item SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class CartItem {

    @Id
    @SequenceGenerator(name = "cart_item_seq",
            sequenceName = "cart_item_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cart_item_seq")
    private Long id;
    private String cartCode;

    @NotNull(message = "Quantity must  not be null")
    private Integer orderQuantity;
    private Long productId;
    private BigDecimal cartTotal;
    private boolean deleted=Boolean.FALSE;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date updatedAt;
}
