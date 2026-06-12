package com.university.saberpro.repository;

import com.university.saberpro.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByNumeroDocumento(String numeroDocumento);

    List<Estudiante> findByFacultadId(Long facultadId);

    boolean existsByNumeroDocumento(String numeroDocumento);
}