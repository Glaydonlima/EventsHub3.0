package com.eventshub.backend.servico;

import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class CriptoServico {

  private static final StrongTextEncryptor criptografador;

  static {

    criptografador = new StrongTextEncryptor();
    criptografador.setPassword("123");
  }

  public static String criptografar(String texto) {
    return criptografador.encrypt(texto);
  }

  public static String descriptografar(String textoCriptografado) {
    return criptografador.decrypt(textoCriptografado);
  }


}
