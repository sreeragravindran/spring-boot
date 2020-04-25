package com.springboot.demo.rest.common;

import com.springboot.demo.common.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;


/*
    A standard response format to be sent for any failure in the api
*/

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ExceptionResponse(){
        this.timestamp = DateTimeUtils.getNowInUTC();
    }

    public ExceptionResponse(Exception ex){
        this.timestamp = DateTimeUtils.getNowInUTC();
        this.message = ex.getMessage();
        this.details = ex.getStackTrace().toString();
    }

}
