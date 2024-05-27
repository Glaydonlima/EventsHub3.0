package com.eventshub.backend.controle;

import com.eventshub.backend.modelo.SolicitacaoModelo;
import com.eventshub.backend.servico.SolicitacaoServico;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/solicitacoes")
public class SolicitacaoControle {

    private final SolicitacaoServico solicitacaoServico;

    @PostMapping("/cadastrar/{idServico}")
    public ResponseEntity<?> salvarSolicitacao(@RequestBody SolicitacaoModelo solicitacao, @PathVariable Long servicoId,HttpServletRequest request) {
        return solicitacaoServico.cadastrarSolicitacao(solicitacao,servicoId, request);
    }

    @PutMapping()
    public ResponseEntity<?> atualizarSolicitacao(@RequestBody SolicitacaoModelo solicitacao) {
        SolicitacaoModelo solicitacaoAtualizada = solicitacaoServico.atualizarSolicitacao(solicitacao);
        if (solicitacaoAtualizada != null) {
            return new ResponseEntity<>(solicitacaoAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Solicitação não encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterSolicitacaoPorId(@PathVariable Long id) {
        SolicitacaoModelo solicitacao = solicitacaoServico.obterSolicitacaoPorId(id);
        if (solicitacao != null) {
            return new ResponseEntity<>(solicitacao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Solicitação não encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listar")
    public Iterable<SolicitacaoModelo> obterTodasSolicitacoes() {
        return solicitacaoServico.obterTodasSolicitacoes();
    }
}
