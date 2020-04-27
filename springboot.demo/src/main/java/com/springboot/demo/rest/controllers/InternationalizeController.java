package com.springboot.demo.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class InternationalizeController {

    @Autowired
    private MessageSource messageSource;


    @GetMapping("/internationalize/greetings")
    public ResponseEntity<?> greetings(@RequestHeader(value = "Accept-Language", required = false) Locale locale){
        return ResponseEntity.ok(messageSource.getMessage("greeting", null, locale));
    }
}
