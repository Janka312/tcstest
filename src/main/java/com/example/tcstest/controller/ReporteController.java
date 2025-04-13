package com.example.tcstest.controller;

import com.example.tcstest.dto.EstadoCuentaReporteDTO;
import com.example.tcstest.service.ReporteService;
import com.example.tcstest.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ApiResponse<EstadoCuentaReporteDTO> generarReporte(
            @RequestParam Long clienteId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {

        EstadoCuentaReporteDTO reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        return new ApiResponse<>(HttpStatus.OK, reporte, "Reporte generado exitosamente");
    }
}