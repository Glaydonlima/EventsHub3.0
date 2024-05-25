package com.eventshub.backend.servico;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventshub.backend.dto.ResponseDTO;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.eventshub.backend.servico.seguranca.TokenServico;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UsuarioServico {

  private final UsuarioRepositorio usuarioRepositorio;

  private final RespostaModelo respostaModelo;

  private final PasswordEncoder passwordEncoder;

  private final TokenServico tokenServico;
  
  public ResponseEntity<?> login(UsuarioModelo usuarioModelo) {
    UsuarioModelo usuario = this.usuarioRepositorio.findByEmail(usuarioModelo.getEmail())
        .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    if (passwordEncoder.matches(usuarioModelo.getSenha(), usuario.getSenha())) {
      String token = this.tokenServico.geradorToken(usuario);
      return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
    }
    return ResponseEntity.badRequest().build();
  }

  public ResponseEntity<?> cadastrar(UsuarioModelo usuarioModelo) { 
        usuarioModelo.setSenha(passwordEncoder.encode(usuarioModelo.getSenha()));
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        usuarioModelo.setRoles(roles);
        String token = tokenServico.geradorToken(usuarioModelo);
        usuarioRepositorio.save(usuarioModelo);
        return new ResponseEntity<>(new ResponseDTO(usuarioModelo.getNome(), token),
            HttpStatus.CREATED);
    } 

       public ResponseEntity<?> Alterar(UsuarioModelo usuarioModelo, HttpServletRequest request){
        Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
      Optional<UsuarioModelo> usuarioExistente = usuarioRepositorio.findById(idUsuario);
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
