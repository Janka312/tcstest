package com.example.tcstest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimientoReporteDTO {
    private LocalDate fecha;
    private String tipoMovimiento;
    private Double valor;
}