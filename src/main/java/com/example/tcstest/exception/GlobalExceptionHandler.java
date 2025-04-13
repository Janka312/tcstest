package com.example.tcstest.exception;

import com.example.tcstest.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, null, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1 // en caso de campos repetidos
                ));

        return new ApiResponse<>(HttpStatus.BAD_REQUEST, errors, "Errores de validación");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleInvalidFormat(HttpMessageNotReadableException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, null, "Formato inválido en la solicitud");
    }

    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<ApiResponse<Void>> handleSaldoNoDisponible(SaldoNoDisponibleException ex) {
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST,
                null,
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, null, ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleGenericException(Exception ex) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, null, "Error interno del servidor: " + ex.getMessage());
    }
}
