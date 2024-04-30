package com.eventshub.backend.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.repositorio.PrestadorRepositorio;

@Service
public class PrestadorServico {

  @Autowired
  private PrestadorRepositorio prestadorRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;

}
