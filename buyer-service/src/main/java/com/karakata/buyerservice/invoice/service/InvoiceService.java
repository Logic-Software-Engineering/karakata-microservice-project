package com.karakata.buyerservice.invoice.service;

import com.karakata.buyerservice.invoice.model.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice createOrderInvoice(Invoice invoice);
    Invoice fetchByInvoiceNumber(String invoiceNumber);
    Invoice fetchInvoiceByOrderNumber(Long orderId);
    List<Invoice> fetchAllInvoices(int pageNumber, int pageSize);
}
