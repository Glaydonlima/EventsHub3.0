package com.eventshub.backend.modelo;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RotasModelo {

  private String rota;
  private HttpMethod metodo;
  private String autorizacao;

}
