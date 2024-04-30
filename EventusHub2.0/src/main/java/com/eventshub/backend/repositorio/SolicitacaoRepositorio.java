package com.eventshub.backend.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventshub.backend.modelo.SolicitacaoModelo;

@Repository
public interface SolicitacaoRepositorio extends CrudRepository<SolicitacaoModelo, Long> {

}
