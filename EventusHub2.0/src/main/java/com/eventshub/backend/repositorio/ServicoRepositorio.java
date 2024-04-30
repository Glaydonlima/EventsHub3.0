package com.eventshub.backend.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventshub.backend.modelo.ServicoModelo;

@Repository
public interface ServicoRepositorio extends CrudRepository<ServicoModelo, Long> {
    
}
