package com.example.tcstest.controller;

import com.example.tcstest.dto.ApiResponse;
import com.example.tcstest.dto.MovimientoDTO;
import com.example.tcstest.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ApiResponse<MovimientoDTO> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        MovimientoDTO nuevoMovimiento = movimientoService.createMovimiento(movimientoDTO);
        return new ApiResponse<>(HttpStatus.CREATED, nuevoMovimiento, "Movimiento creado exitosamente");
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ApiResponse<List<MovimientoDTO>> getAllMovimientosByCuentaId(@PathVariable Long cuentaId) {
        List<MovimientoDTO> movimientos = movimientoService.getAllMovimientosByCuentaId(cuentaId);
        return new ApiResponse<>(HttpStatus.OK, movimientos, "Movimientos obtenidos correctamente");
    }

    @GetMapping("/{id}")
    public ApiResponse<MovimientoDTO> getMovimientoById(@PathVariable Long id) {
        MovimientoDTO movimiento = movimientoService.getMovimientoById(id);
        return new ApiResponse<>(HttpStatus.OK, movimiento, "Movimiento encontrado");
    }

    @PutMapping("/{id}")
    public ApiResponse<MovimientoDTO> updateMovimiento(@PathVariable Long id, @Valid @RequestBody MovimientoDTO movimientoDTO) {
        MovimientoDTO movimientoActualizado = movimientoService.updateMovimiento(id, movimientoDTO);
        return new ApiResponse<>(HttpStatus.OK, movimientoActualizado, "Movimiento actualizado con éxito");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, null, "Movimiento eliminado con éxito");
    }
}