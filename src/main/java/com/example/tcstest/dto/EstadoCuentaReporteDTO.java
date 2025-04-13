package com.example.tcstest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstadoCuentaReporteDTO {
    private Long clienteId;
    private String clienteNombre;
    private List<CuentaReporteDTO> cuentas;
}