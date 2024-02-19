package com.karakata.userservice.appuserservice.buyer.exception;


import com.karakata.userservice.appuserservice.errormessage.ValidateErrorMessage;
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
public class BuyerNotFoundExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(BuyerNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> buyerNotFoundException(BuyerNotFoundException bnfe, WebRequest request){
        ValidateErrorMessage vem=new ValidateErrorMessage(HttpStatus.NOT_FOUND, bnfe.getMessage(),
                request.getDescription(false), new Date());
        return new ResponseEntity<>(vem, HttpStatus.BAD_REQUEST);
    }
}
