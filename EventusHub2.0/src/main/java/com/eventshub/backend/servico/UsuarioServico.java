package com.eventshub.backend.servico;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventshub.backend.dto.ResponseDTO;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.eventshub.backend.servico.seguranca.TokenServico;

@Service
public class UsuarioServico {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TokenServico tokenServico;


  public ResponseEntity<?> login(UsuarioModelo usuarioModelo){
    UsuarioModelo usuario = this.usuarioRepositorio.findByEmail(usuarioModelo.getEmail())
        .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    if (passwordEncoder.matches(usuarioModelo.getSenha(), usuario.getSenha())) {
      String token = this.tokenServico.geradorToken(usuario);
      return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
    }
    return ResponseEntity.badRequest().build();
  }

  public ResponseEntity<?> cadastrarAlterar(UsuarioModelo usuarioModelo, String acao, Long id) {
    if (acao.equals("cadastrar")) {
      Optional<UsuarioModelo> usuario = usuarioRepositorio.findByEmail(usuarioModelo.getEmail());
        if(usuario.isEmpty()) {
          usuarioModelo.setSenha(passwordEncoder.encode(usuarioModelo.getSenha()));
            String token = tokenServico.geradorToken(usuarioModelo);
            usuarioRepositorio.save(usuarioModelo);
            return new ResponseEntity<>(new ResponseDTO(usuarioModelo.getNome(), token), HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
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
          usuarioExistenteAtualizado.setSenha(passwordEncoder.encode(senha));
        }
        if (usuarioRepositorio.existsByEmail(usuarioModelo.getEmail())) {
          respostaModelo.setMensagem("O email já está cadastrado!");
          return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UsuarioModelo>(
            usuarioRepositorio.save(usuarioExistenteAtualizado), HttpStatus.OK);
      } else {
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
      }
    } else {
      respostaModelo.setMensagem("Ação inválida!");
      return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
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
