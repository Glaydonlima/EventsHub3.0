package com.eventshub.backend.servico.seguranca;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepositorio repositorio;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioModelo usuario = this.repositorio.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
        usuario.getSenha(), new ArrayList<>());
  }

}
