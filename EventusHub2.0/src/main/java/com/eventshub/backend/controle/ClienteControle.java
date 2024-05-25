package com.eventshub.backend.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eventshub.backend.modelo.ClienteModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.servico.ClienteServico;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/clientes")
public class ClienteControle {

    private final ClienteServico clienteServico;

    @PostMapping()
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody ClienteModelo clienteModelo, HttpServletRequest request) {
        return clienteServico.cadastrar(clienteModelo, request);
    }

    @Secured({"ROLE_CLIENTE", "ADMIN"})
    @PutMapping()
    public ResponseEntity<?> alterarCliente(@Valid @RequestBody ClienteModelo clienteModelo, HttpServletRequest request) {
        return clienteServico.alterar(clienteModelo, request);
    }

    @Secured({"ROLE_CLIENTE", "ADMIN"})
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<RespostaModelo> removerCliente(@PathVariable Long clienteId) {
        return clienteServico.remover(clienteId);
    }

    @Secured({"ROLE_CLIENTE", "ADMIN"})
    @GetMapping()
    public Iterable<ClienteModelo> listarClientes() {
        return clienteServico.listar();
    }

}
