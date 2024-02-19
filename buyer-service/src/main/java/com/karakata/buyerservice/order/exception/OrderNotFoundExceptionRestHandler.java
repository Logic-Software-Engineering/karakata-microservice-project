package com.karakata.buyerservice.order.exception;


import com.karakata.buyerservice.errormessage.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class OrderNotFoundExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> orderNotFoundException(OrderNotFoundException onfe, WebRequest request) {
        ValidateErrorMessage vem = new ValidateErrorMessage(HttpStatus.NOT_FOUND, onfe.getMessage(),
                request.getDescription(false), new Date());
        return new ResponseEntity<>(vem, HttpStatus.BAD_REQUEST);
    }
}
