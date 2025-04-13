package com.example.tcstest.service;

import java.time.format.DateTimeFormatter;

import com.example.tcstest.dto.CuentaReporteDTO;
import com.example.tcstest.dto.EstadoCuentaReporteDTO;
import com.example.tcstest.dto.MovimientoReporteDTO;
import com.example.tcstest.exception.ResourceNotFoundException;
import com.example.tcstest.model.Cliente;
import com.example.tcstest.model.Cuenta;
import com.example.tcstest.model.Movimiento;
import com.example.tcstest.repository.ClienteRepository;
import com.example.tcstest.repository.MovimientoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final MovimientoRepository movimientoRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    public ReporteService(MovimientoRepository movimientoRepository,
                          ClienteRepository clienteRepository,
                          ModelMapper modelMapper) {
        this.movimientoRepository = movimientoRepository;
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public EstadoCuentaReporteDTO generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {

        Cliente cliente = obtenerCliente(clienteId);

        EstadoCuentaReporteDTO reporteDTO = new EstadoCuentaReporteDTO();
        reporteDTO.setClienteId(cliente.getClienteId());
        reporteDTO.setClienteNombre(cliente.getNombre());

        List<CuentaReporteDTO> cuentasDTO = generarCuentasDTO(cliente, fechaInicio, fechaFin);
        reporteDTO.setCuentas(cuentasDTO);

        return reporteDTO;
    }

    private LocalDate convertirStringAFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

    private Cliente obtenerCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));
    }
    private List<CuentaReporteDTO> generarCuentasDTO(Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        return cliente.getCuentas().stream()
                .map(cuenta -> {
                    CuentaReporteDTO cuentaDTO = new CuentaReporteDTO();
                    cuentaDTO.setCuentaId(cuenta.getCuentaId());
                    cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
                    cuentaDTO.setSaldoActual(cuenta.getSaldoInicial());

                    List<MovimientoReporteDTO> movimientosDTO = obtenerMovimientosDTO(cuenta, fechaInicio, fechaFin);
                    cuentaDTO.setMovimientos(movimientosDTO);

                    return cuentaDTO;
                })
                .collect(Collectors.toList());
    }
    private List<MovimientoReporteDTO> obtenerMovimientosDTO(Cuenta cuenta, LocalDate fechaInicio, LocalDate fechaFin) {

        List<Movimiento> movimientos = movimientoRepository
                .findByCuentaCuentaIdAndFechaBetween(cuenta.getCuentaId(), fechaInicio, fechaFin);

        return movimientos.stream()
                .map(mov -> modelMapper.map(mov, MovimientoReporteDTO.class))
                .collect(Collectors.toList());

    }
}