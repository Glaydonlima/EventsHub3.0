package com.eventshub.backend.servico;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.eventshub.backend.modelo.PrestadorModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.PrestadorRepositorio;
import com.eventshub.backend.repositorio.UsuarioRepositorio;

@Service
public class PrestadorServico {
  @Autowired
  private PrestadorRepositorio prestadorRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  private boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  private ResponseEntity<RespostaModelo> createErrorResponse(String message) {
    respostaModelo.setMensagem(message);
    return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<?> alterar(PrestadorModelo prestadorModelo, Long idUsuario) {
        if (isNullOrEmpty(prestadorModelo.getRazaoSocial())) {
          return createErrorResponse("A razão social é obrigatória!");
    }
    if (isNullOrEmpty(prestadorModelo.getCpf())) {
      return createErrorResponse("O Cpf ou Cnpj é obrigatório!");
    }
    if (isNullOrEmpty(prestadorModelo.getDescricaoEmpresa())) {
      return createErrorResponse("A descrição da empresa é obrigatória!");
    }
    if (prestadorRepositorio.existsByCpf(prestadorModelo.getCpf())) {
      return createErrorResponse("O Cpf ou Cnpj já está em uso!");
    }
      prestadorRepositorio.save(prestadorModelo);
      return new ResponseEntity<>(prestadorModelo, HttpStatus.OK);
  }

  public ResponseEntity<?> cadastrar(PrestadorModelo prestadorModelo, Long idUsuario) {
    UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    prestadorModelo.setUsuario(usuario);
    return new ResponseEntity<PrestadorModelo>(prestadorRepositorio.save(prestadorModelo),
        HttpStatus.OK); 
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

  public ResponseEntity<?> buscarPorId(Long id) {
    Optional<PrestadorModelo> prestador = prestadorRepositorio.findById(id);
    if (prestador.isPresent()) {
      return new ResponseEntity<>(prestador.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  

}
