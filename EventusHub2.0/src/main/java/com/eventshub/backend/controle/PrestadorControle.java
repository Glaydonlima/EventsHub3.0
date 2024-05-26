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
import com.eventshub.backend.modelo.PrestadorModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.servico.PrestadorServico;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/prestador")
public class PrestadorControle {

  private final PrestadorServico prestadorServico;

  @PostMapping()
  public ResponseEntity<?> cadastrar(@Valid @RequestBody PrestadorModelo prestadorModelo,
      HttpServletRequest request) {
    return prestadorServico.cadastrar(prestadorModelo, request);
  }
  @Secured({"ROLE_PRESTADOR", "ADMIN"})
  @PutMapping()
  public ResponseEntity<?> alterar(@Valid @RequestBody PrestadorModelo prestadorModelo,
  HttpServletRequest request) {
    return prestadorServico.alterar(prestadorModelo, request);
  }
  @Secured({"ROLE_PRESTADOR", "ADMIN"})
  @DeleteMapping("/{id}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable Long id) {
    return prestadorServico.remover(id);
  }
  @Secured({"ROLE_PRESTADOR", "ADMIN"})
  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
    return prestadorServico.buscarPorId(id);
  }
  @Secured({"ROLE_PRESTADOR", "ADMIN"})
  @GetMapping("/listar")
  public Iterable<PrestadorModelo> listarClientes() {
    return prestadorServico.listar();
  }

}


