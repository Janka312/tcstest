package com.example.tcstest.cofig;

import com.example.tcstest.dto.ClienteDTO;
import com.example.tcstest.dto.CuentaDTO;
import com.example.tcstest.dto.MovimientoDTO;
import com.example.tcstest.model.Cliente;
import com.example.tcstest.model.Cuenta;
import com.example.tcstest.model.Movimiento;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CuentaDTO.class, Cuenta.class).addMappings(mapper -> {
            mapper.skip(Cuenta::setCuentaId);
            mapper.skip(Cuenta::setCliente);
        });

        modelMapper.typeMap(ClienteDTO.class, Cliente.class).addMappings(mapper -> {
            mapper.skip(Cliente::setClienteId);
        });

        modelMapper.typeMap(MovimientoDTO.class, Movimiento.class).addMappings(mapper -> {
            mapper.skip(Movimiento::setMovimientoId);
            mapper.skip(Movimiento::setFecha);
        });

        return modelMapper;
    }
}