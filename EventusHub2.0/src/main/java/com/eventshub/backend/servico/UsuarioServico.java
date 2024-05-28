package com.eventshub.backend.servico;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eventshub.backend.dto.LoginRequestDTO;
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

  private final PasswordEncoder passwordEncoder;

  private final TokenServico tokenServico;
  
  public ResponseEntity<?> login(final LoginRequestDTO usuarioDto) {
    final UsuarioModelo usuario = usuarioRepositorio.findByEmail(usuarioDto.email())
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

    if (passwordEncoder.matches(usuarioDto.senha(), usuario.getSenha())) {
        final String token = tokenServico.geradorToken(usuario);
        return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
    }

    return ResponseEntity.badRequest().build();
}

public ResponseEntity<?> cadastrar(final UsuarioModelo usuarioModelo) {
  if (usuarioRepositorio.existsByEmail(usuarioModelo.getEmail())) {
      return new ResponseEntity<>("O email já está cadastrado!", HttpStatus.BAD_REQUEST);
  }

  usuarioModelo.setSenha(passwordEncoder.encode(usuarioModelo.getSenha()));
  Set<String> roles = new HashSet<>();
  roles.add("ROLE_USER");
  usuarioModelo.setRoles(roles);
  String token = tokenServico.geradorToken(usuarioModelo);
  usuarioRepositorio.save(usuarioModelo);

  return new ResponseEntity<>(new ResponseDTO(usuarioModelo.getNome(), token), HttpStatus.CREATED);
}

       public ResponseEntity<?> Alterar(final UsuarioModelo usuarioModelo, final HttpServletRequest request) {
    final Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
    final Optional<UsuarioModelo> optionalUsuario = usuarioRepositorio.findById(idUsuario);

    if (!optionalUsuario.isPresent()) {
        return new ResponseEntity<>("Usuario não encontrado", HttpStatus.NOT_FOUND);
    }

    final UsuarioModelo usuarioExistente = optionalUsuario.get();
    if (usuarioModelo.getNome() != null && !usuarioModelo.getNome().isEmpty()) {
        usuarioExistente.setNome(usuarioModelo.getNome());
    }
    if (usuarioModelo.getEmail() != null && !usuarioModelo.getEmail().isEmpty()) {
        if (usuarioRepositorio.existsByEmail(usuarioModelo.getEmail())) {
            return new ResponseEntity<>("O email já está cadastrado!", HttpStatus.BAD_REQUEST);
        }
        usuarioExistente.setEmail(usuarioModelo.getEmail());
    }
    if (usuarioModelo.getSenha() != null && !usuarioModelo.getSenha().isEmpty()) {
        usuarioExistente.setSenha(passwordEncoder.encode(usuarioModelo.getSenha()));
    }

    return new ResponseEntity<>(usuarioRepositorio.save(usuarioExistente), HttpStatus.OK);
}

public ResponseEntity<RespostaModelo> remover(final long id) {
  final Optional<UsuarioModelo> optionalUsuario = usuarioRepositorio.findById(id);
  if (optionalUsuario.isPresent()) {
      usuarioRepositorio.delete(optionalUsuario.get());
      final RespostaModelo respostaModelo = new RespostaModelo();
      respostaModelo.setMensagem("O usuario foi removido com sucesso");
      return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
  } else {
      final RespostaModelo respostaModelo = new RespostaModelo();
      respostaModelo.setMensagem("Usuario não encontrado");
      return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
  }
}

  public Iterable<UsuarioModelo> listar() {
    return usuarioRepositorio.findAll();
  }

  
}
