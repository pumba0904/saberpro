package com.university.saberpro.repository;

import com.university.saberpro.model.ResultadoSaberPro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResultadoSaberProRepository extends JpaRepository<ResultadoSaberPro, Long> {
    List<ResultadoSaberPro> findByEstudianteId(Long estudianteId);
    Optional<ResultadoSaberPro> findTopByEstudianteIdOrderByFechaCargueDesc(Long estudianteId);
}