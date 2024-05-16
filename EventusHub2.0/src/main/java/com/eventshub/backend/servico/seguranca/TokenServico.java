package com.eventshub.backend.servico.seguranca;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
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
      String token = JWT.create().withIssuer("login-auth-api").withSubject(usuario.getEmail())
          .withExpiresAt(this.expiradorJWT()).sign(algoritimo);
      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro na autentificação");

    }
  }

  public String validarToken(String token) {
    try {
      Algorithm algoritimo = Algorithm.HMAC256("segredo");
      return JWT.require(algoritimo).withIssuer("login-auth-api").build().verify(token)
          .getSubject();
    } catch (JWTVerificationException exception) {
      return null;
    }
  }

  private Instant expiradorJWT() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
