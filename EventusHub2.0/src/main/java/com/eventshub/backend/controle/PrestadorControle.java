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
import com.eventshub.backend.modelo.PrestadorModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.servico.PrestadorServico;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/prestador")
public class PrestadorControle {

  @Autowired
  private PrestadorServico prestadorServico;

  @PostMapping("/cadastrar/{id}")
  public ResponseEntity<?> cadastrar(@RequestBody PrestadorModelo prestadorModelo,
      @PathVariable Long id) {
    return prestadorServico.cadastrarAlterar(prestadorModelo, "cadastrar", id);
  }

  @PutMapping("/alterar/{id}")
  public ResponseEntity<?> alterar(@RequestBody PrestadorModelo prestadorModelo,
      @PathVariable Long id) {
    return prestadorServico.cadastrarAlterar(prestadorModelo, "alterar", id);
  }

  @DeleteMapping("/remover/{id}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable Long id) {
    return prestadorServico.remover(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
    return prestadorServico.buscarPorId(id);
  }
}


