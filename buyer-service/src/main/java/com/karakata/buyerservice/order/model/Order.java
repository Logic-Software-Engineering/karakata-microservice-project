package com.karakata.buyerservice.order.model;

import com.karakata.buyerservice.cartitem.model.CartItem;
import com.karakata.buyerservice.staticdata.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE orders SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Order {
    @Id
    @SequenceGenerator(name = "order_seq",
            sequenceName = "order_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "order_seq")
    private Long id;
    private String orderNumber;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Transient
    private BigDecimal vat = new BigDecimal(7.5);

    private LocalDateTime expectedDeliveryDate;
    private BigDecimal orderSubTotal;
    private BigDecimal orderTotal;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date updatedAt;
}
