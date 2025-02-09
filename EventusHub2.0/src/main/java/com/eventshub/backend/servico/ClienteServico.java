package com.eventshub.backend.servico;

import java.util.Optional;
import java.util.Set;

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

    try{
        Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
        UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
          Set<String> roles = usuario.getRoles();
          roles.add("ROLE_CLIENTE");
          usuario.setRoles(roles);
          usuarioRepositorio.save(usuario);
          clienteModelo.setUsuario(usuario);
          clienteModelo.setId(usuario.getId());
          return new ResponseEntity<>(clienteRepositorio.save(clienteModelo), HttpStatus.CREATED);

          } catch(Exception e){return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);}
   }

   public ResponseEntity<?> alterar(ClienteModelo clienteModelo, HttpServletRequest request) {
    try{

      Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
      
      Optional<ClienteModelo> clienteExistente = clienteRepositorio.findById(idUsuario);
      if (!clienteExistente.isPresent()) {
        respostaModelo.setMensagem("Cliente não encontrado");
        return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
    
    ClienteModelo clienteExistenteAtualizado = clienteExistente.get();
    Optional.ofNullable(clienteModelo.getEndereco())
        .filter(endereco -> !endereco.isEmpty())
        .ifPresent(clienteExistenteAtualizado::setEndereco);
    
    Optional.ofNullable(clienteModelo.getTelefone())
        .filter(telefone -> !telefone.isEmpty())
        .ifPresent(clienteExistenteAtualizado::setTelefone);
    
    return new ResponseEntity<>(
        clienteRepositorio.save(clienteExistenteAtualizado), HttpStatus.OK);
    }catch (Exception e) {
        respostaModelo.setMensagem("Erro ao atualizar cliente: " + e.getMessage());
        return new ResponseEntity<>(respostaModelo, HttpStatus.INTERNAL_SERVER_ERROR);
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
