package com.karakata.buyerservice.invoice.dto;


import com.karakata.buyerservice.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private String invoiceNumber;
    private Date invoiceDate;
    private Order order;
    private BigDecimal orderTotal;
    private BigDecimal vat;
    private BigDecimal invoiceTotalAmount;
}
