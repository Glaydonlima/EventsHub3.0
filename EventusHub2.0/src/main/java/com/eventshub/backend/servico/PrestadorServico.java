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
import com.eventshub.backend.servico.seguranca.TokenServico;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PrestadorServico {
  @Autowired
  private PrestadorRepositorio prestadorRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private TokenServico tokenServico;
  

  public ResponseEntity<?> cadastrarAlterar(PrestadorModelo prestadorModelo, String acao,
      HttpServletRequest request) {

        Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
    if (prestadorModelo.getRazaoSocial() == null || prestadorModelo.getRazaoSocial().isEmpty()) {
      respostaModelo.setMensagem("A razão social é obrigatória!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }
    if (prestadorModelo.getCpf() == null || prestadorModelo.getCpf().isEmpty()) {
      respostaModelo.setMensagem("O Cpf ou Cnpj é obrigatório!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }
    if (prestadorModelo.getDescricaoEmpresa() == null
        || prestadorModelo.getDescricaoEmpresa().isEmpty()) {
      respostaModelo.setMensagem("A descrição da empresa é obrigatória!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }
    if (prestadorRepositorio.existsByCpf(prestadorModelo.getCpf())) {
      respostaModelo.setMensagem("O Cpf ou Cnpj já está em uso!");
      return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
    }
    if (acao.equalsIgnoreCase("cadastrar")) {
      UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
      prestadorModelo.setUsuario(usuario);
      return new ResponseEntity<PrestadorModelo>(prestadorRepositorio.save(prestadorModelo),
          HttpStatus.OK);
    } else if (acao.equalsIgnoreCase("alterar")) {
      return new ResponseEntity<PrestadorModelo>(prestadorRepositorio.save(prestadorModelo),
          HttpStatus.OK);
    } else {
      return ResponseEntity.badRequest().body("Ação inválida");
    }
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
