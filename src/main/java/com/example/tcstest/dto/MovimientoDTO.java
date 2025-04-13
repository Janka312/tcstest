package com.example.tcstest.dto;

import com.example.tcstest.model.TipoMovimiento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimientoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long movimientoId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate fecha;

    @NotNull(message = "El tipo de movimiento no puede ser nulo")
    private TipoMovimiento tipoMovimiento;

    @NotNull(message = "El valor no puede ser nulo")
    private Double valor;

    private Double saldo;

    @NotNull(message = "La cuenta asociada no puede ser nula")
    private Long cuentaId;
}