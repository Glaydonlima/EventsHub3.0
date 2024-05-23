package com.eventshub.backend.servico.seguranca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventshub.backend.modelo.RotasModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SegurancaFiltro extends OncePerRequestFilter {

  @Autowired
  private TokenServico tokenServico;

  @Autowired
  private AntPathMatcher antPathMatcher;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;



  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
  @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
    var token = recuperarToken(request);
    if (token != null) {
      var login = tokenServico.validarToken(token);
      if (login != null) {
        Optional<UsuarioModelo> usuarioOpt = usuarioRepositorio.findByEmail(login);
        if (usuarioOpt.isPresent()) {
          UsuarioModelo usuario = usuarioOpt.get();
          var authorities  = usuario.getRoles().stream()
                                   .map(role -> new SimpleGrantedAuthority(role))
                                   .collect(Collectors.toList());
          var authentication = new UsernamePasswordAuthenticationToken(usuario,null, authorities);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          
          if (!temPermissao(request, authorities)) {
            enviarRespostaErro(response, "Acesso negado.");
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


  private boolean temPermissao(HttpServletRequest request, List<SimpleGrantedAuthority> authorities) {
    String path = request.getRequestURI();
    String method = request.getMethod();
    
    // for (RotasModelo rota : rotasModeloConfig()) {
    //     if (rota.getRota() != null && rota.getMetodo() != null
    //             && antPathMatcher.match(rota.getRota(), path) && rota.getMetodo().name().equals(method)) {
    //         return authorities.contains(new SimpleGrantedAuthority(rota.getAutorizacao()));
    //     }
    // }
    
    return true; 
}



  private String recuperarToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null)
      return null;
    return authHeader.replace("Bearer ", "");
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

  @Bean
    public List<RotasModelo> rotasModeloConfig() {
        List<RotasModelo> rotas = new ArrayList<>();
        rotas.add(new RotasModelo("/usuario/remover/{id}", HttpMethod.DELETE, "ADMIN"));
        rotas.add(new RotasModelo("/usuario/alterar/{id}", HttpMethod.PUT, "ROLE_USER"));
        rotas.add(new RotasModelo("/usuario/listar", HttpMethod.GET, "ADMIN"));
        
        rotas.add(new RotasModelo("/prestador/cadastrar/{id}", HttpMethod.POST, "ROLE_USER"));
        rotas.add(new RotasModelo("/prestador/alterar/{id}", HttpMethod.PUT, "ROLE_USER"));
        rotas.add(new RotasModelo("/prestador/remover/{id}", HttpMethod.DELETE, "ADMIN"));
        rotas.add(new RotasModelo("/prestador/{id}", HttpMethod.GET, "ROLE_USER"));
        rotas.add(new RotasModelo("/prestador/listar", HttpMethod.GET, "ADMIN"));
        
        return rotas;
    }

}
