package com.example.tcstest.controller;

import com.example.tcstest.dto.ApiResponse;
import com.example.tcstest.dto.ClienteDTO;
import com.example.tcstest.model.Cliente;
import com.example.tcstest.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ApiResponse<Cliente> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.createCliente(clienteDTO);
        return new ApiResponse<>(HttpStatus.CREATED, cliente, "Cliente creado con éxito");
    }

    @GetMapping
    public ApiResponse<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.getAllClientes();
        return new ApiResponse<>(HttpStatus.OK, clientes, "Clientes obtenidos con éxito");
    }

    @GetMapping("/{id}")
    public ApiResponse<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.getClienteById(id);
        return new ApiResponse<>(HttpStatus.OK, clienteDTO, "Cliente obtenido con éxito");
    }

    @PutMapping("/{id}")
    public ApiResponse<ClienteDTO> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO updatedCliente = clienteService.updateCliente(id, clienteDTO);
        return new ApiResponse<>(HttpStatus.OK, updatedCliente, "Cliente actualizado con éxito");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, null, "Cliente eliminado con éxito");
    }

}
