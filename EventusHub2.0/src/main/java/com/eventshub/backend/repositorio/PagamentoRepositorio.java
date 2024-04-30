package com.eventshub.backend.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventshub.backend.modelo.PagamentoModelo;

@Repository
public interface PagamentoRepositorio extends CrudRepository<PagamentoModelo, Long> {

}
