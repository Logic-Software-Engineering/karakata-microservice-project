package com.karakata.authserver.appuser.exception;


import com.karakata.authserver.errormessage.ValidateErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class UserNotFoundExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> userNotFoundException(UserNotFoundException unfe, WebRequest request){
        ValidateErrorMessage vem= new ValidateErrorMessage(HttpStatus.NOT_FOUND,
                unfe.getMessage(), request.getDescription(false), new Date());
        return new ResponseEntity<>(vem, HttpStatus.BAD_REQUEST);
    }
}
