package com.eventshub.backend.servico;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.eventshub.backend.modelo.PrestadorModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.repositorio.PrestadorRepositorio;

@Service
public class PrestadorServico {
  @Autowired
  private PrestadorRepositorio prestadorRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;

  public ResponseEntity<?> cadastrarAlterar(PrestadorModelo prestadorModelo, String acao) {
    Optional<PrestadorModelo> prestadorExistente =
        prestadorRepositorio.findById(prestadorModelo.getId());
    if (prestadorExistente.isPresent() && acao.equalsIgnoreCase("cadastrar")) {
      respostaModelo.setMensagem("Prestador já cadastrado");
      return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }

    if (!prestadorExistente.isPresent() && acao.equalsIgnoreCase("alterar")) {
      respostaModelo.setMensagem("Prestador não encontrado");
      return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<PrestadorModelo>(prestadorRepositorio.save(prestadorModelo),
        HttpStatus.CREATED);
  }

  public ResponseEntity<RespostaModelo> remover(long id) {
    if (prestadorRepositorio.existsById(id)) {
      prestadorRepositorio.deleteById(id);
      respostaModelo.setMensagem("O prestador foi removido com sucesso");
      return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    } else {
      respostaModelo.setMensagem("Prestador não encontrado");
      return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
  }

  public Iterable<PrestadorModelo> listar() {
    return prestadorRepositorio.findAll();
  }

}
