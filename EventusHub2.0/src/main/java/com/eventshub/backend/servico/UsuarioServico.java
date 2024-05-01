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


  public ResponseEntity<?> cadastrarAlterar(UsuarioModelo usuarioModelo, String acao, Long id) {
    if (acao.equals("cadastrar")) {
      if (usuarioModelo.getNome() == null || usuarioModelo.getNome().isEmpty()) {
        respostaModelo.setMensagem("O nome do produto é obrigatório!");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
      } else if (usuarioModelo.getEmail() == null || usuarioModelo.getEmail().isEmpty()) {
        respostaModelo.setMensagem("O Email é obrigatório!");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
      } else if (usuarioModelo.getSenha() == null || usuarioModelo.getSenha().isEmpty()) {
        respostaModelo.setMensagem("A senha é obrigatória!");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
      }
      String senha = usuarioModelo.getSenha();
      usuarioModelo.setSenha(CriptoServico.criptografar(senha));
      return new ResponseEntity<UsuarioModelo>(usuarioRepositorio.save(usuarioModelo),
          HttpStatus.CREATED);
    } else if (acao.equals("alterar")) {
      Optional<UsuarioModelo> usuarioExistente = usuarioRepositorio.findById(id);
      if (usuarioExistente.isPresent()) {
        UsuarioModelo usuarioExistenteAtualizado = usuarioExistente.get();
        if (usuarioModelo.getNome() != null && !usuarioModelo.getNome().isEmpty()) {
          usuarioExistenteAtualizado.setNome(usuarioModelo.getNome());
        }
        if (usuarioModelo.getEmail() != null && !usuarioModelo.getEmail().isEmpty()) {
          usuarioExistenteAtualizado.setEmail(usuarioModelo.getEmail());
        }
        if (usuarioModelo.getSenha() != null && !usuarioModelo.getSenha().isEmpty()) {
          String senha = usuarioModelo.getSenha();
          usuarioExistenteAtualizado.setSenha(CriptoServico.criptografar(senha));
        }
        return new ResponseEntity<UsuarioModelo>(
            usuarioRepositorio.save(usuarioExistenteAtualizado), HttpStatus.OK);
      } else {
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
      }
    } else {
      respostaModelo.setMensagem("Ação inválida!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }
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
