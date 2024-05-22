package com.eventshub.backend.repositorio;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventshub.backend.modelo.UsuarioModelo;

@Repository
public interface UsuarioRepositorio extends CrudRepository<UsuarioModelo, Long> {
  boolean existsByEmail(String email);

  Optional<UsuarioModelo> findByEmail(String email);

;

}
