package com.university.saberpro.repository;

import com.university.saberpro.model.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FacultadRepository extends JpaRepository<Facultad, Long> {
    Optional<Facultad> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
