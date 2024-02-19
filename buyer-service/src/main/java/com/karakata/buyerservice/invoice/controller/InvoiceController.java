package com.karakata.buyerservice.invoice.controller;

import com.karakata.buyerservice.invoice.dto.InvoiceResponse;
import com.karakata.buyerservice.invoice.model.Invoice;
import com.karakata.buyerservice.invoice.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/karakata/invoice")
public record InvoiceController(InvoiceService invoiceService) {

    @GetMapping("/findInvoiceByNumber")
    public ResponseEntity<InvoiceResponse> getByInvoiceNumber(@RequestParam("invoiceNumber") String invoiceNumber) {
        Invoice invoice=invoiceService.fetchByInvoiceNumber(invoiceNumber);
        InvoiceResponse invoiceResponse=convertInvoiceToDto(invoice);
        return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
    }

    @GetMapping("/findInvoiceByOrderNumber")
    public ResponseEntity<InvoiceResponse> getInvoiceByOrderNumber(@RequestParam("orderNumber") Long orderNumber){
        Invoice invoice=invoiceService.fetchInvoiceByOrderNumber(orderNumber);
        InvoiceResponse invoiceResponse=convertInvoiceToDto(invoice);
        return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
    }

    @GetMapping("/findAllInvoices")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices(@RequestParam("pageNumber") int pageNumber,
                                                                @RequestParam("pageSize") int pageSize){
        return new ResponseEntity<>(invoiceService.fetchAllInvoices(pageNumber, pageSize)
                .stream().map(this::convertInvoiceToDto).collect(Collectors.toList()), HttpStatus.OK);
    }


    private InvoiceResponse convertInvoiceToDto(Invoice invoice){
        InvoiceResponse invoiceResponse=new InvoiceResponse();
        invoiceResponse.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceResponse.setInvoiceDate(invoice.getInvoiceDate());
        invoiceResponse.setOrder(invoice.getOrder());
        invoiceResponse.setOrderTotal(invoice.getOrderTotal());
        invoiceResponse.setVat(invoice.getVat());
        invoiceResponse.setInvoiceTotalAmount(invoice.getInvoiceTotalAmount());
        return invoiceResponse;
    }
}
