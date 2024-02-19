package com.karakata.buyerservice.invoice.repository;

import com.karakata.buyerservice.invoice.model.Invoice;
import com.karakata.buyerservice.order.model.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvoicesRepositoryCustom {
    @Query("From Invoice i Where i.invoiceNumber=?1")
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    @Query("From Invoice i where i.order=?1")
    Invoice invoiceByOrderId(Order order);

}
