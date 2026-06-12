package com.university.saberpro.repository;

import com.university.saberpro.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByCedula(String cedula);
    List<Docente> findByFacultadId(Long facultadId);
    boolean existsByCedula(String cedula);
}