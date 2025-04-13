package com.example.tcstest.exception;

public class SaldoNoDisponibleException extends RuntimeException {

    public SaldoNoDisponibleException(String message) {
        super(message);
    }
}