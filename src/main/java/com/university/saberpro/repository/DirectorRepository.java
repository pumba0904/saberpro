package com.university.saberpro.repository;

import com.university.saberpro.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Optional<Director> findByCedula(String cedula);
    List<Director> findByFacultadId(Long facultadId);
    boolean existsByCedula(String cedula);
}