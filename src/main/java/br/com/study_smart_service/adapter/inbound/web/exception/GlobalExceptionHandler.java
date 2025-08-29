package br.com.study_smart_service.adapter.inbound.web.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Ocorreu um erro inesperado";

        if (ex instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }
        else if (ex instanceof MethodArgumentNotValidException manvEx) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            message = manvEx.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Erro de validação");
        }
        else if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        }
        else if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
            message = ex.getMessage();
        }
        else if (ex instanceof ResponseStatusException rse) {
            status = (HttpStatus) rse.getStatusCode();
            message = rse.getReason() != null ? rse.getReason() : rse.getMessage();
        }

        Map<String, Object> body = new HashMap<>();
        body.put("error", message);
        body.put("code", status.value());

        log.error(ex.getMessage(), ex);

        return ResponseEntity
                .status(status)
                .body(body);
    }
}
