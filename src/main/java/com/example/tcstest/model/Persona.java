package com.example.tcstest.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Persona {

    private String nombre;

    private String genero;

    private Integer edad;

    @Column(unique = true)
    private String identificacion;

    private String direccion;

    private String telefono;
}
