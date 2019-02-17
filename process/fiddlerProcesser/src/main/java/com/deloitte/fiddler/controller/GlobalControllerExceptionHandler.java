package com.deloitte.fiddler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class) 
    public ResponseEntity<String> reponseMyException(HttpServerErrorException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getResponseBodyAsString());
    }
}