package com.eventshub.backend.servico;

import java.util.Optional;
import java.util.Set;

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
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class PrestadorServico {

  private final PrestadorRepositorio prestadorRepositorio;

  private final RespostaModelo respostaModelo;

  private final UsuarioRepositorio usuarioRepositorio;

  private final TokenServico tokenServico;

  public ResponseEntity<?> alterar(PrestadorModelo prestadorModelo, HttpServletRequest request) {
  try{
    Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
      Optional<PrestadorModelo> prestadorExistente = prestadorRepositorio.findById(idUsuario);
      if (!prestadorExistente.isPresent()) {
        respostaModelo.setMensagem("Prestador não encontrado");
        return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
    }
    
    PrestadorModelo prestadorExistenteAtualizado = prestadorExistente.get();
    Optional.ofNullable(prestadorModelo.getRazaoSocial())
        .filter(razaoSocial -> !razaoSocial.isEmpty())
        .ifPresent(prestadorExistenteAtualizado::setRazaoSocial);
    
    Optional.ofNullable(prestadorModelo.getCnpjCpf())
        .filter(cnpjCpf -> !cnpjCpf.isEmpty())
        .ifPresent(prestadorExistenteAtualizado::setCnpjCpf);

    Optional.ofNullable(prestadorModelo.getDescricaoEmpresa())
        .filter(descricaoEmpresa -> !descricaoEmpresa.isEmpty())
        .ifPresent(prestadorExistenteAtualizado::setDescricaoEmpresa);

      Optional.ofNullable(prestadorModelo.getPortfolio())
        .filter(portfolio -> !portfolio.isEmpty())
        .ifPresent(prestadorExistenteAtualizado::setPortfolio);

    return new ResponseEntity<>(
        prestadorRepositorio.save(prestadorExistenteAtualizado), HttpStatus.OK);
    } catch(Exception e){return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);}
  }
  
  public ResponseEntity<?> cadastrar(PrestadorModelo prestadorModelo, HttpServletRequest request) {
  try{
    Long idUsuario = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
    Optional<PrestadorModelo> prestadorExistente = prestadorRepositorio.findById(idUsuario);
    if (prestadorExistente.isPresent()) {
      respostaModelo.setMensagem("Prestador já cadastrado");
      return new ResponseEntity<>(respostaModelo,HttpStatus.BAD_REQUEST);}
    UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
          Set<String> roles = usuario.getRoles();
            roles.add("ROLE_PRESTADOR");
            usuario.setRoles(roles);
            usuarioRepositorio.save(usuario);
      prestadorModelo.setUsuario(usuario);
      prestadorModelo.setId(usuario.getId());
      return new ResponseEntity<PrestadorModelo>(prestadorRepositorio.save(prestadorModelo),
        HttpStatus.OK);
      }catch(Exception e){return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);}      
}


public ResponseEntity<RespostaModelo> remover(long id) {
  long countBeforeDelete = prestadorRepositorio.count();
  prestadorRepositorio.deleteById(id);
  long countAfterDelete = prestadorRepositorio.count();

  if (countBeforeDelete > countAfterDelete) {
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
