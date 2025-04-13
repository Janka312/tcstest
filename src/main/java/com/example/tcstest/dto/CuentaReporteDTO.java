package com.example.tcstest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CuentaReporteDTO {
    private Long cuentaId;
    private String numeroCuenta;
    private Double saldoActual;
    private List<MovimientoReporteDTO> movimientos;
}