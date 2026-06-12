package com.university.saberpro.service;

import com.university.saberpro.model.Estudiante;
import com.university.saberpro.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Optional<Estudiante> buscarPorDocumento(String numeroDocumento) {
        return estudianteRepository.findByNumeroDocumento(numeroDocumento);
    }

    public List<Estudiante> listarPorFacultad(Long facultadId) {
        return estudianteRepository.findByFacultadId(facultadId);
    }

    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }

    public boolean existeDocumento(String numeroDocumento) {
        return estudianteRepository.existsByNumeroDocumento(numeroDocumento);
    }
}