package com.karakata.buyerservice.errormessage;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidateErrorMessage {
    private HttpStatus httpStatus;
    private String message;
    private String description;
    private Date date;
}
