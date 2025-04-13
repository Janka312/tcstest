package com.example.tcstest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long clienteId;

    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotNull(message = "El género es obligatorio")
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "La edad no puede ser mayor a 120")
    private Integer edad;

    @NotNull(message = "La identificación es obligatoria")
    @Size(min = 10, max = 15, message = "La identificación debe tener entre 10 y 15 caracteres")
    private String identificacion;

    @NotNull(message = "La dirección es obligatoria")
    @Size(min = 10, max = 100, message = "La dirección debe tener entre 10 y 100 caracteres")
    private String direccion;

    @NotNull(message = "El teléfono es obligatorio")
    @Size(min = 10, max = 15, message = "El teléfono debe tener entre 10 y 15 caracteres")
    private String telefono;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String contrasena;

    @NotNull(message = "El estado es obligatorio")
    @Pattern(regexp = "true|false", message = "El estado debe ser 'true' o 'false'")
    private String estado;
}
