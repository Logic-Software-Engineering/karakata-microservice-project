package com.karakata.buyerservice.invoice.exception;

import com.karakata.buyerservice.errormessage.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class InvoiceNotFoundExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> invoiceNotFoundException(InvoiceNotFoundException infe, WebRequest webRequest){
        ValidateErrorMessage vem= new ValidateErrorMessage(HttpStatus.NOT_FOUND,infe.getMessage(),
                webRequest.getDescription(false), new Date());
        return new ResponseEntity<>(vem, HttpStatus.BAD_REQUEST);
    }

}
