package com.eventshub.backend.servico;

import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventshub.backend.modelo.ClienteModelo;
import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.ClienteRepositorio;
import com.eventshub.backend.repositorio.UsuarioRepositorio;
import com.eventshub.backend.servico.seguranca.TokenServico;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ClienteServico {
  
  private final ClienteRepositorio clienteRepositorio;

  
  private final RespostaModelo respostaModelo;

  
  private final UsuarioRepositorio usuarioRepositorio;

  
  private final TokenServico tokenServico;

   public ResponseEntity<?> cadastrar(ClienteModelo clienteModelo,  HttpServletRequest request) {
        Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
        UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
      clienteModelo.setUsuario(usuario);
      clienteModelo.setId(usuario.getId());
      return new ResponseEntity<>(clienteRepositorio.save(clienteModelo), HttpStatus.CREATED);
   }

   public ResponseEntity<?> alterar(ClienteModelo clienteModelo, HttpServletRequest request) {
      Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
      Optional<ClienteModelo> clienteExistente = clienteRepositorio.findById(idUsuario);
      if (clienteExistente.isPresent()) {
        ClienteModelo clienteExistenteAtualizado = clienteExistente.get();
        if (clienteModelo.getEndereco() != null && !clienteModelo.getEndereco().isEmpty()) {
          clienteExistenteAtualizado.setEndereco(clienteModelo.getEndereco());
        }
        if (clienteModelo.getTelefone() != null && !clienteModelo.getTelefone().isEmpty()) {
          clienteExistenteAtualizado.setTelefone(clienteModelo.getTelefone());
        }
        return new ResponseEntity<ClienteModelo>(
            clienteRepositorio.save(clienteExistenteAtualizado), HttpStatus.OK);
      } else {
        respostaModelo.setMensagem("Cliente não encontrado");
        return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
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
