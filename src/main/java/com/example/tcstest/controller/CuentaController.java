package com.example.tcstest.controller;

import com.example.tcstest.dto.ApiResponse;
import com.example.tcstest.dto.CuentaDTO;
import com.example.tcstest.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.tcstest.repository.CuentaRepository;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ApiResponse<CuentaDTO> createCuenta(@Valid @RequestBody CuentaDTO cuentaDTO) {
        CuentaDTO cuentaCreada = cuentaService.createCuenta(cuentaDTO);
        return new ApiResponse<>(HttpStatus.CREATED, cuentaCreada, "Cuenta creada con éxito");
    }

    @GetMapping
    public ApiResponse<List<CuentaDTO>> getAllCuentas() {
        List<CuentaDTO> cuentas = cuentaService.getAllCuentas();
        return new ApiResponse<>(HttpStatus.OK, cuentas, "Cuentas obtenidas con éxito");
    }

    @GetMapping("/{id}")
    public ApiResponse<CuentaDTO> getCuentaById(@PathVariable Long id) {
        CuentaDTO cuentaDTO = cuentaService.getCuentaById(id);
        return new ApiResponse<>(HttpStatus.OK, cuentaDTO, "Cuenta obtenida con éxito");
    }

    @PutMapping("/{id}")
    public ApiResponse<CuentaDTO> updateCuenta(@PathVariable Long id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        CuentaDTO updatedCuenta = cuentaService.updateCuenta(id, cuentaDTO);
        return new ApiResponse<>(HttpStatus.OK, updatedCuenta, "Cuenta actualizada con éxito");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, null, "Cuenta eliminada con éxito");
    }


}