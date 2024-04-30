package com.eventshub.backend.servico;

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


  public ResponseEntity<?> cadastrarAlterar(UsuarioModelo um, String acao) {

    if (um.getNome().equals("")) {
      respostaModelo.setMensagem("O nome do produto é obrigatorio!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    } else if (um.getEmail().equals("")) {
      respostaModelo.setMensagem("O Email é obrigatorio!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    } else if (um.getSenha().equals("")) {
      respostaModelo.setMensagem("A senha é obrigatoria!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<UsuarioModelo>(usuarioRepositorio.save(um), HttpStatus.CREATED);

  }


  public ResponseEntity<RespostaModelo> remover(long id) {

    usuarioRepositorio.deleteById(id);

    respostaModelo.setMensagem("O produto foi removido com sucesso");
    return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);

  }


  public Iterable<UsuarioModelo> listar() {
    return usuarioRepositorio.findAll();
  }

}
