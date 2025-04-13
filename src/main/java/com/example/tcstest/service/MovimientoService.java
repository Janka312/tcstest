package com.example.tcstest.service;

import com.example.tcstest.dto.MovimientoDTO;
import com.example.tcstest.exception.ResourceNotFoundException;
import com.example.tcstest.exception.SaldoNoDisponibleException;
import com.example.tcstest.model.Cuenta;
import com.example.tcstest.model.Movimiento;
import com.example.tcstest.model.TipoMovimiento;
import com.example.tcstest.repository.CuentaRepository;
import com.example.tcstest.repository.MovimientoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO) {
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + movimientoDTO.getCuentaId()));

        double nuevoSaldo = getNuevoSaldo(movimientoDTO, cuenta);

        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        movimiento.setFecha(LocalDate.now());
        movimiento.setCuenta(cuenta);
        movimiento.setSaldo(nuevoSaldo);

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        
        Movimiento nuevoMovimiento = movimientoRepository.save(movimiento);
        return modelMapper.map(nuevoMovimiento, MovimientoDTO.class);
    }

    private static double getNuevoSaldo(MovimientoDTO movimientoDTO, Cuenta cuenta) {
        double nuevoSaldo = cuenta.getSaldoInicial();

        if (movimientoDTO.getTipoMovimiento() == TipoMovimiento.DEPOSITO) {
            nuevoSaldo += movimientoDTO.getValor();
        } else if (movimientoDTO.getTipoMovimiento() == TipoMovimiento.RETIRO) {
            nuevoSaldo -= movimientoDTO.getValor();
            if (nuevoSaldo < 0) {
                throw new SaldoNoDisponibleException("Saldo no disponible para la cuenta con ID: " + movimientoDTO.getCuentaId());
            }
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido");
        }
        return nuevoSaldo;
    }

    public List<MovimientoDTO> getAllMovimientosByCuentaId(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + cuentaId));

        List<Movimiento> movimientos = movimientoRepository.findByCuentaCuentaId(cuentaId);

        return movimientos.stream()
                .map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class))
                .collect(Collectors.toList());
    }

    public MovimientoDTO getMovimientoById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));

        return modelMapper.map(movimiento, MovimientoDTO.class);
    }

    public MovimientoDTO updateMovimiento(Long id, MovimientoDTO movimientoDTO) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));

        modelMapper.map(movimientoDTO, movimiento);
        Movimiento movimientoActualizado = movimientoRepository.save(movimiento);
        return modelMapper.map(movimientoActualizado, MovimientoDTO.class);
    }

    public void deleteMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con ID: " + id));

        movimientoRepository.delete(movimiento);
    }

}