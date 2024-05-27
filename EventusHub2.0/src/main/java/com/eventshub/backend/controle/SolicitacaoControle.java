package com.eventshub.backend.controle;

import com.eventshub.backend.modelo.SolicitacaoModelo;
import com.eventshub.backend.servico.SolicitacaoServico;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/solicitacoes")
public class SolicitacaoControle {

    private final SolicitacaoServico solicitacaoServico;

    public SolicitacaoControle(SolicitacaoServico solicitacaoServico) {
        this.solicitacaoServico = solicitacaoServico;
    }

    @PostMapping()
    public ResponseEntity<?> salvarSolicitacao(@RequestBody SolicitacaoModelo solicitacao, HttpServletRequest request) {
        return solicitacaoServico.cadastrarSolicitacao(solicitacao, request);
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
