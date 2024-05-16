package com.eventshub.backend.servico;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eventshub.backend.modelo.SolicitacaoModelo;
import com.eventshub.backend.repositorio.SolicitacaoRepositorio;

@Service
public class SolicitacaoServico {

   @Autowired
    private SolicitacaoRepositorio solicitacaoRepositorio;

    public SolicitacaoModelo salvarSolicitacao(SolicitacaoModelo solicitacao) {
        solicitacao.setDataCriacao(LocalDateTime.now());
        solicitacao.setDataAtualizacao(LocalDateTime.now());
        return solicitacaoRepositorio.save(solicitacao);
    }
    public SolicitacaoModelo atualizarSolicitacao(SolicitacaoModelo solicitacao) {
      solicitacao.setDataAtualizacao(LocalDateTime.now());
      return solicitacaoRepositorio.save(solicitacao);
  }

  public SolicitacaoModelo obterSolicitacaoPorId(Long id) {
      return solicitacaoRepositorio.findById(id).orElse(null);
  }

  public Iterable<SolicitacaoModelo> obterTodasSolicitacoes() {
      return solicitacaoRepositorio.findAll();
  }


}
