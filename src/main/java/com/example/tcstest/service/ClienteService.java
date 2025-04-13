package com.example.tcstest.service;

import com.example.tcstest.dto.ClienteDTO;
import com.example.tcstest.exception.ResourceNotFoundException;
import com.example.tcstest.exception.ResourceAlreadyExistsException;
import com.example.tcstest.model.Cliente;
import com.example.tcstest.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Cliente createCliente(ClienteDTO clienteDTO) {

        if (clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())) {
            throw new ResourceAlreadyExistsException("La identificación " + clienteDTO.getIdentificacion() + " ya está registrada.");
        }

        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return clienteRepository.save(cliente);
    }

    public List<ClienteDTO> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));


        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        if (clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion()) &&
                !cliente.getIdentificacion().equals(clienteDTO.getIdentificacion())) {
            throw new ResourceAlreadyExistsException("La identificación " + clienteDTO.getIdentificacion() + " ya está registrada.");
        }

        modelMapper.map(clienteDTO, cliente);
        Cliente updatedCliente = clienteRepository.save(cliente);
        return modelMapper.map(updatedCliente, ClienteDTO.class);
    }

    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }

}
