package com.eventshub.backend.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventshub.backend.modelo.RespostaModelo;
import com.eventshub.backend.modelo.UsuarioModelo;
import com.eventshub.backend.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio ur;

    @Autowired
    private RespostaModelo rm;

    
  public ResponseEntity<?> cadastrarAlterar(UsuarioModelo um, String acao){

    if(um.getNome().equals("")){
      rm.setMensagem("O nome do produto é obrigatorio!");
      return new ResponseEntity<RespostaModelo>(rm, HttpStatus.BAD_REQUEST);
    }else if(um.getEmail().equals("")){
      rm.setMensagem("O Email é obrigatorio!");
      return new ResponseEntity<RespostaModelo>(rm, HttpStatus.BAD_REQUEST);
    }
    else if(um.getSenha().equals("")){
        rm.setMensagem("A senha é obrigatoria!");
        return new ResponseEntity<RespostaModelo>(rm, HttpStatus.BAD_REQUEST);}

        return new ResponseEntity<UsuarioModelo>(ur.save(um), HttpStatus.CREATED);
      
    }
  

  public ResponseEntity<RespostaModelo> remover(long id){

    ur.deleteById(id);

    rm.setMensagem("O produto foi removido com sucesso");
    return new ResponseEntity<RespostaModelo>(rm, HttpStatus.OK);

  }


    public Iterable<UsuarioModelo> listar(){
        return ur.findAll();
      }

}
