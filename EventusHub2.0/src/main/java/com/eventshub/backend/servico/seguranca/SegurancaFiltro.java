package com.eventshub.backend.servico.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class SegurancaFiltro extends OncePerRequestFilter {

  @Autowired
  private TokenServico tokenServico;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
  @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
    var token = recuperarToken(request);
    System.out.println(token);
    if (token != null) {
      var login = tokenServico.validarToken(token);
      if (login != null) {
        Optional<UsuarioModelo> usuarioOpt = usuarioRepositorio.findByEmail(login);
        if (usuarioOpt.isPresent()) {
          UsuarioModelo usuario = usuarioOpt.get();
          if (!usuario.hasRole("ADMIN") || usuario.hasRole("ROLE_USER")) {
            enviarRespostaErro(response, "Você não tem permissão para acessar este recurso.");
            return;
          }
        } else {
          enviarRespostaErro(response, "Usuário não encontrado.");
          return;
        }
      }
    }

    filterChain.doFilter(request, response);
  }


  private Set<String> temPermissao(HttpServletRequest request) {
    String token = recuperarToken(request);
    System.out.println("Token recebido: " + token);

    if (token != null) {
      UsuarioModelo usuario = buscarUsuarioPorToken(token);
      if (usuario != null) {
        return usuario.getRoles();
      }
    }
    return Collections.emptySet();
  }



  private UsuarioModelo buscarUsuarioPorToken(String token) {
    System.out.println(token);
    String login = tokenServico.validarToken(token);
    if (login != null) {
      return usuarioRepositorio.findByEmail(login).orElse(null);
    }
    return null;
  }

  private String recuperarToken(HttpServletRequest request) {
    System.out.println(request);
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null)
      return null;
    return authHeader.replace("Bearer ", "");
  }

  private void enviarRespostaErro(HttpServletResponse response, String mensagem)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("error", mensagem);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(response.getWriter(), responseBody);
  }
}
