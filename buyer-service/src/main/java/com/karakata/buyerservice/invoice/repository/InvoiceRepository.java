package com.karakata.buyerservice.invoice.repository;

import com.karakata.buyerservice.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String>,InvoicesRepositoryCustom {
}
