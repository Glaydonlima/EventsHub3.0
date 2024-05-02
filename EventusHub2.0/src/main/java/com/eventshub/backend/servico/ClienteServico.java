package com.eventshub.backend.servico;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventshub.backend.modelo.ClienteModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.ClienteRepositorio;
import com.eventshub.backend.repositorio.UsuarioRepositorio;

@Service
public class ClienteServico {
  @Autowired
  private ClienteRepositorio clienteRepositorio;

  @Autowired
  private RespostaModelo respostaModelo;

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

   public ResponseEntity<?> cadastrarAlterar(ClienteModelo clienteModelo, String acao, Long id) {
    if (acao.equals("cadastrar")) {
        UsuarioModelo usuario = usuarioRepositorio.findById(id)
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
      clienteModelo.setUsuario(usuario);
      return new ResponseEntity<>(clienteRepositorio.save(clienteModelo), HttpStatus.CREATED);
    } else if (acao.equals("alterar")) {
      Optional<ClienteModelo> clienteExistente = clienteRepositorio.findById(id);
      if (clienteExistente.isPresent()) {
        ClienteModelo clienteExistenteAtualizado = clienteExistente.get();
        return new ResponseEntity<ClienteModelo>(
            clienteRepositorio.save(clienteExistenteAtualizado), HttpStatus.OK);
      } else {
        respostaModelo.setMensagem("Cliente não encontrado");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
      }
    } else {
      respostaModelo.setMensagem("Ação inválida!");
      return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<RespostaModelo> remover(long id) {
    if (clienteRepositorio.existsById(id)) {
      clienteRepositorio.deleteById(id);
      respostaModelo.setMensagem("O cliente foi removido com sucesso");
      return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    } else {
      respostaModelo.setMensagem("Cliente não encontrado");
      return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
  }

  public Iterable<ClienteModelo> listar() {
    return clienteRepositorio.findAll();
  }


}
