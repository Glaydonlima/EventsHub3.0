package com.eventshub.backend.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventshub.backend.modelo.PrestadorModelo;

@Repository
public interface PrestadorRepositorio extends CrudRepository<PrestadorModelo, Long> {

  boolean existsByCpf(String cpf);

}
