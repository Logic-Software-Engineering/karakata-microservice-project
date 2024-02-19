package com.karakata.buyerservice.invoice.model;

import com.karakata.buyerservice.order.model.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
