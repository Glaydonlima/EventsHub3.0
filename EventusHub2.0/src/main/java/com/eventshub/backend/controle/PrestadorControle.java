package com.eventshub.backend.controle;

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
import com.eventshub.backend.modelo.PrestadorModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.servico.PrestadorServico;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/prestador")
public class PrestadorControle {

  private final PrestadorServico prestadorServico;

  @PostMapping("/{id}")
  public ResponseEntity<?> cadastrar(@RequestBody PrestadorModelo prestadorModelo,
      @PathVariable Long idUsuario) {
    return prestadorServico.cadastrar(prestadorModelo, idUsuario);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> alterar(@RequestBody PrestadorModelo prestadorModelo,
      @PathVariable Long idUsuario) {
    return prestadorServico.alterar(prestadorModelo, idUsuario);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable Long id) {
    return prestadorServico.remover(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
    return prestadorServico.buscarPorId(id);
  }

  @GetMapping("/listar")
  public Iterable<PrestadorModelo> listarClientes() {
    return prestadorServico.listar();
  }

}


