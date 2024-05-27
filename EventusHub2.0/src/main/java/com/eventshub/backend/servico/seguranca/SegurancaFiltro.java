package com.eventshub.backend.servico.seguranca;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class SegurancaFiltro extends OncePerRequestFilter {

  
  private final TokenServico tokenServico;

  
  private final UsuarioRepositorio usuarioRepositorio;

  @Override
protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
  var token = tokenServico.recuperarToken(request);
    if (token != null) {
        var login = tokenServico.validarToken(token);
        if (login != null) {
            Optional<UsuarioModelo> usuarioOpt = usuarioRepositorio.findByEmail(login);
            if (usuarioOpt.isPresent()) {
                UsuarioModelo usuario = usuarioOpt.get();
                var authorities = usuario.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                enviarRespostaErro(response, "Usuário não encontrado.");
                return;
            }
        }
    }
    filterChain.doFilter(request, response);
}

  private void enviarRespostaErro(HttpServletResponse response, String mensagem)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json;charset=UTF-8");
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("error", mensagem);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(response.getWriter(), responseBody);
  }
}
