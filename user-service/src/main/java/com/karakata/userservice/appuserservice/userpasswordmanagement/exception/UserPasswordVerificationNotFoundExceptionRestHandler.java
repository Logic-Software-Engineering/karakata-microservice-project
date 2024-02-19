package com.karakata.userservice.appuserservice.userpasswordmanagement.exception;


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
public class UserPasswordVerificationNotFoundExceptionRestHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserPasswordVerificationNotFoundException.class)
    public ResponseEntity<ValidateErrorMessage> userPasswordVerificationNotFoundException(UserPasswordVerificationNotFoundException upvnfe,
                                                                                          WebRequest request){
        ValidateErrorMessage vem =new ValidateErrorMessage(HttpStatus.NOT_FOUND,upvnfe.getMessage(),
                request.getDescription(false), new Date());
        return new ResponseEntity<>(vem, HttpStatus.BAD_REQUEST);
    }
}
