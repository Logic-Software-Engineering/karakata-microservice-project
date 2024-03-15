package com.karakata.buyerservice.invoice.model;

import com.karakata.buyerservice.order.model.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE cart_item SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Invoice {
    @Id
    private String invoiceNumber;
    private BigDecimal orderTotal;

    @CreationTimestamp
    private Date invoiceDate;

    @Transient
    private BigDecimal vat = new BigDecimal(7.5);
    private BigDecimal invoiceTotalAmount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Order order;
    private boolean deleted=Boolean.FALSE;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
