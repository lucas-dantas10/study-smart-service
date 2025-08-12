package br.com.study_smart_service.adapter.inbound.web.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        else if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        }
        else if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        }
        else if (ex instanceof ResponseStatusException rse) {
            status = (HttpStatus) rse.getStatusCode();
        }

        Map<String, Object> body = new HashMap<>();
        body.put("error", ex.getMessage());
        body.put("code", status.value());

        return ResponseEntity
                .status(status)
                .body(body);
    }
}
