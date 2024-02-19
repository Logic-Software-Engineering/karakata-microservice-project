package com.karakata.buyerservice.invoice.service;

import com.karakata.buyerservice.invoice.exception.InvoiceNotFoundException;
import com.karakata.buyerservice.invoice.model.Invoice;
import com.karakata.buyerservice.invoice.repository.InvoiceRepository;
import com.karakata.buyerservice.order.exception.OrderNotFoundException;
import com.karakata.buyerservice.order.model.Order;
import com.karakata.buyerservice.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Invoice createOrderInvoice(Invoice invoice) {
        invoice.setInvoiceNumber("Inv".concat(String.valueOf(100+new Random()
                .nextInt(LocalDateTime.now().getNano()))));
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice fetchByInvoiceNumber(String invoiceNumber) {

        return invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(()->new InvoiceNotFoundException("Invoice number "+invoiceNumber+" not found"));
    }

    @Override
    public Invoice fetchInvoiceByOrderNumber(Long orderId) {
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order ID " + orderId + " not found"));

        return invoiceRepository.invoiceByOrderId(order);
    }

    @Override
    public List<Invoice> fetchAllInvoices(int pageNumber, int pageSize) {
        return invoiceRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }
}
