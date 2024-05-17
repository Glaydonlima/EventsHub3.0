package com.eventshub.backend.servico;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eventshub.backend.modelo.FotoModelo;
import com.eventshub.backend.modelo.ServicoModelo;
import com.eventshub.backend.repositorio.FotoRepositorio;
import com.eventshub.backend.repositorio.ServicoRepositorio;

import java.util.List;

@Service
public class ServicoServico {

  @Autowired
  private ServicoRepositorio servicoRepositorio;

  @Autowired
  private FotoRepositorio fotoRepositorio;

  public ServicoModelo salvarServico(ServicoModelo servico) {
    return servicoRepositorio.save(servico);
  }

  public FotoModelo salvarFoto(FotoModelo foto) {
    return fotoRepositorio.save(foto);
  }

  public List<FotoModelo> obterFotosPorServico(Long servicoId) {
    return fotoRepositorio.findByServicoId(servicoId);
  }

  public ServicoModelo obterServicoPorId(Long servicoId) {
    return servicoRepositorio.findById(servicoId).orElse(null);
  }

}
