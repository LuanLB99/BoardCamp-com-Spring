package com.boardcamp_test.boardcamp_test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> conflictException(ConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro de Conflito: " + ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + ex.getMessage());
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<String> unprocessableException(UnprocessableException ex){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Erro: " + ex.getMessage());
    }
}