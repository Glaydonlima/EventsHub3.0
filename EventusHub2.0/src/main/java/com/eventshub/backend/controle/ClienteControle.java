package com.eventshub.backend.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clientes")
public class ClienteControle {

    @Autowired
    private ClienteServico clienteServico;

    @PostMapping("/cadastrar/{id}")
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody ClienteModelo clienteModelo, @PathVariable Long id) {
        return clienteServico.cadastrarAlterar(clienteModelo, "cadastrar", id);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarCliente(@Valid @RequestBody ClienteModelo clienteModelo, @PathVariable Long id) {
        return clienteServico.cadastrarAlterar(clienteModelo, "alterar", id);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<RespostaModelo> removerCliente(@PathVariable Long id) {
        return clienteServico.remover(id);
    }

    @GetMapping("/listar")
    public Iterable<ClienteModelo> listarClientes() {
        return clienteServico.listar();
    }

}
