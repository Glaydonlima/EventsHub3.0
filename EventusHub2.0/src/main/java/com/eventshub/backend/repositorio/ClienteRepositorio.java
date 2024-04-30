package com.eventshub.backend.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventshub.backend.modelo.ClienteModelo;

@Repository
public interface ClienteRepositorio extends CrudRepository<ClienteModelo, Long> {

}
