package com.eventshub.backend.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.servico.UsuarioServico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioControle {

  @Autowired
  private UsuarioServico usuarioServico;

  @DeleteMapping("remover/{id}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable long id) {
    return usuarioServico.remover(id);
  }

  @PutMapping("alterar/{id}")
  public ResponseEntity<?> alterar(@RequestBody UsuarioModelo usuarioModelo,
      @PathVariable Long id) {
    return usuarioServico.cadastrarAlterar(usuarioModelo, "alterar", id);
  }


  @PostMapping("cadastrar")
  public ResponseEntity<?> cadastar(@RequestBody UsuarioModelo usuarioModelo) {
    return usuarioServico.cadastrarAlterar(usuarioModelo, "cadastrar", null);
  }

  @GetMapping("listar")
  public Iterable<UsuarioModelo> listar() {
    return usuarioServico.listar();
  }


}
