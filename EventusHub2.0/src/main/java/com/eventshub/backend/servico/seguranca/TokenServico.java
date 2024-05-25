package com.eventshub.backend.servico.seguranca;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.eventshub.backend.modelo.UsuarioModelo;


@Service
public class TokenServico {
  
  public String geradorToken(UsuarioModelo usuario) {
    try {
      Algorithm algoritimo = Algorithm.HMAC256("segredo");
      String token = JWT.create().withIssuer("EventsHub2.0").withSubject(usuario.getEmail())
          .withClaim("roles", getRoles(usuario))
          .withExpiresAt(this.expiradorJWT()).sign(algoritimo);
      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro na autentificação");

    }
  }

  public String validarToken(String token) {
    try {
      Algorithm algoritimo = Algorithm.HMAC256("segredo");
      return JWT.require(algoritimo).withIssuer("EventsHub2.0").build().verify(token)
          .getSubject();
    } catch (JWTVerificationException exception) {
      return null;
    }
  }

  private Instant expiradorJWT() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  private List<String> getRoles(UsuarioModelo usuario) {
    return usuario.getRoles().stream().toList();
  }
}
