package com.example.cliniciaOdonto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //Maneja de manera global todas las excepciones
public class GlobalException {
    @ExceptionHandler({ResourceNotFoundException.class}) //Le ponemos que exception va a manejar
    public ResponseEntity<String> tratamientoResourceNotFoundException(ResourceNotFoundException rnfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("mensaje: " + rnfe);
    }
}
