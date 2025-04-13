package com.example.tcstest.service;

import com.example.tcstest.dto.CuentaDTO;
import com.example.tcstest.exception.ResourceAlreadyExistsException;
import com.example.tcstest.exception.ResourceNotFoundException;
import com.example.tcstest.model.Cliente;
import com.example.tcstest.model.Cuenta;
import com.example.tcstest.model.TipoCuenta;
import com.example.tcstest.repository.ClienteRepository;
import com.example.tcstest.repository.CuentaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;


    public CuentaDTO createCuenta(CuentaDTO cuentaDTO) {

        Cliente cliente = clienteRepository.findById(cuentaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + cuentaDTO.getClienteId()));

        if (cuentaRepository.existsByNumeroCuenta(cuentaDTO.getNumeroCuenta())) {
            throw new ResourceAlreadyExistsException("El número de cuenta " + cuentaDTO.getNumeroCuenta() + " ya está registrado");
        }

        Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
        cuenta.setCliente(cliente);
        Cuenta nuevaCuenta = cuentaRepository.save(cuenta);

        return modelMapper.map(nuevaCuenta, CuentaDTO.class);
    }

    public List<CuentaDTO> getAllCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentas.stream()
                .map(cuenta -> modelMapper.map(cuenta, CuentaDTO.class))
                .collect(Collectors.toList());
    }

    public CuentaDTO getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        return modelMapper.map(cuenta, CuentaDTO.class);
    }

    public CuentaDTO updateCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        if (!cuenta.getNumeroCuenta().equals(cuentaDTO.getNumeroCuenta()) &&
                cuentaRepository.existsByNumeroCuenta(cuentaDTO.getNumeroCuenta())) {
            throw new ResourceAlreadyExistsException("El número de cuenta " + cuentaDTO.getNumeroCuenta() + " ya está registrado");
        }

        Cliente cliente = clienteRepository.findById(cuentaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + cuentaDTO.getClienteId()));

        modelMapper.map(cuentaDTO, cuenta);
        cuenta.setCliente(cliente);

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
        return modelMapper.map(cuentaActualizada, CuentaDTO.class);
    }

    public void deleteCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        cuentaRepository.delete(cuenta);
    }


}