package com.eventshub.backend.servico.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import com.eventshub.backend.modelo.ClienteModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import java.io.IOException;
import java.util.Collections;

@Component
public class SegurancaFiltro extends OncePerRequestFilter {

  @Autowired
  TokenServico tokenServico;

  @Autowired
  UsuarioRepositorio usuarioRepositorio;

  @Override
  protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response,
     @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
    var token = this.recuperarToken(request);
    var login = tokenServico.validarToken(token);

    if (login != null) {
      UsuarioModelo usuario = usuarioRepositorio.findByEmail(login)
          .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
      var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
      var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String method = request.getMethod();
        if ("PUT".equalsIgnoreCase(method) || "POST".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            String userIdPath = getUserIdFromPath(request.getRequestURI());
            if (userIdPath != null && !userIdPath.equals(usuario.getId().toString())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Você não tem permissão para modificar estas informações.");
                return;
            }
        }

    }
    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null)
      return null;
    return authHeader.replace("Bearer ", "");
  }

  private String getUserIdFromPath(String uri) {
    String[] parts = uri.split("/");
    return parts.length > 2 ? parts[parts.length - 1] : null;
}

  
}
