package com.eventshub.backend.servico;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.eventshub.backend.modelo.FotoModelo;
import com.eventshub.backend.modelo.PrestadorModelo;
import com.eventshub.backend.modelo.ServicoModelo;
import com.eventshub.backend.repositorio.FotoRepositorio;
import com.eventshub.backend.repositorio.PrestadorRepositorio;
import com.eventshub.backend.repositorio.ServicoRepositorio;
import com.eventshub.backend.servico.seguranca.TokenServico;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Service
public class ServicoServico {

  @Autowired
  private ServicoRepositorio servicoRepositorio;

  @Autowired
  private FotoRepositorio fotoRepositorio;

  @Autowired
  private TokenServico tokenServico;

  @Autowired
  private PrestadorRepositorio prestadorRepositorio;


  public Iterable<ServicoModelo> listarServicos() {
    return servicoRepositorio.findAll();
  }

  public ResponseEntity<?> salvarServico(ServicoModelo servicoModelo, HttpServletRequest request) {

    try{
        Long idPrestador = tokenServico.extrairIdUsuarioDoToken(tokenServico.recuperarToken(request));
        PrestadorModelo prestador = prestadorRepositorio.findById(idPrestador)
          .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
          servicoModelo.setPrestador(prestador);
          return new ResponseEntity<>(servicoRepositorio.save(servicoModelo), HttpStatus.CREATED);

          } catch(Exception e){return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
   }
  

  public FotoModelo salvarFoto(FotoModelo foto) {
    return fotoRepositorio.save(foto);
  }

  public List<FotoModelo> obterFotosPorServico(Long servicoId) {
    return fotoRepositorio.findByServicoId(servicoId);
  }

  public ServicoModelo obterServicoPorId(Long servicoId) {
    return servicoRepositorio.findById(servicoId).orElse(null);
  }

}
