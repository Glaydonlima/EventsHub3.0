package com.eventshub.backend.servico;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;


  public ResponseEntity<?> cadastrarAlterar(UsuarioModelo usuarioModelo, String acao) {

    if (usuarioModelo.getNome().equals("")) {
      respostaModelo.setMensagem("O nome do produto é obrigatorio!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    } else if (usuarioModelo.getEmail().equals("")) {
      respostaModelo.setMensagem("O Email é obrigatorio!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    } else if (usuarioModelo.getSenha().equals("")) {
      respostaModelo.setMensagem("A senha é obrigatoria!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    String senha = usuarioModelo.getSenha();
    usuarioModelo.setSenha(CriptoServico.criptografar(senha));

    return new ResponseEntity<UsuarioModelo>(usuarioRepositorio.save(usuarioModelo),
        HttpStatus.CREATED);

  }

  public ResponseEntity<RespostaModelo> remover(long id) {
    if (usuarioRepositorio.existsById(id)) {
      usuarioRepositorio.deleteById(id);
      respostaModelo.setMensagem("O usuario foi removido com sucesso");
      return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    } else {
      respostaModelo.setMensagem("Usuario não encontrado");
      return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
  }

  public Iterable<UsuarioModelo> listar() {
    return usuarioRepositorio.findAll();
  }

}
