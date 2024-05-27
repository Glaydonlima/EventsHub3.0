package com.eventshub.backend.servico;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventshub.backend.modelo.ClienteModelo;
import com.eventshub.backend.modelo.ServicoModelo;
import com.eventshub.backend.modelo.SolicitacaoModelo;
import com.eventshub.backend.repositorio.ClienteRepositorio;
import com.eventshub.backend.repositorio.ServicoRepositorio;
import com.eventshub.backend.repositorio.SolicitacaoRepositorio;
import com.eventshub.backend.servico.seguranca.TokenServico;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SolicitacaoServico {

   
    private final SolicitacaoRepositorio solicitacaoRepositorio;

    private final TokenServico tokenServico;

    private final ClienteRepositorio clienteRepositorio;

    private final ServicoRepositorio servicoRepositorio;

    public ResponseEntity<?> cadastrarSolicitacao(SolicitacaoModelo solicitacao, Long idServico, HttpServletRequest request) {
        try{
        ServicoModelo servico = servicoRepositorio.findById(idServico).orElseThrow(() -> new RuntimeException("Servico não encontrado"));
        Long idCliente = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
        ClienteModelo cliente = clienteRepositorio.findById(idCliente)
          .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
          solicitacao.setCliente(cliente);
          solicitacao.setDataCriacao(LocalDateTime.now());
          solicitacao.setDataAtualizacao(LocalDateTime.now());
          solicitacao.setServico(servico);
          return new ResponseEntity<>(solicitacaoRepositorio.save(solicitacao), HttpStatus.CREATED);

          } catch(Exception e){return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
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
