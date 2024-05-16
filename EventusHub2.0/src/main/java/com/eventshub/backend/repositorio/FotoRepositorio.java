package com.eventshub.backend.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eventshub.backend.modelo.FotoModelo;

@Repository
public interface FotoRepositorio extends JpaRepository<FotoModelo, Long> {

  List<FotoModelo> findByServicoId(Long servicoId);

}
