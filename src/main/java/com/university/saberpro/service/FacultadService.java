package com.university.saberpro.service;

import com.university.saberpro.model.Facultad;
import com.university.saberpro.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FacultadService {

    @Autowired
    private FacultadRepository facultadRepository;

    public List<Facultad> listarTodas() {
        return facultadRepository.findAll();
    }

    public Optional<Facultad> buscarPorId(Long id) {
        return facultadRepository.findById(id);
    }

    public Facultad guardar(Facultad facultad) {
        return facultadRepository.save(facultad);
    }

    public void eliminar(Long id) {
        facultadRepository.deleteById(id);
    }

    public boolean existeNombre(String nombre) {
        return facultadRepository.existsByNombre(nombre);
    }
}