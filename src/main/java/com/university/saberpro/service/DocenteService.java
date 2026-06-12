package com.university.saberpro.service;

import com.university.saberpro.model.Docente;
import com.university.saberpro.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> listarTodos() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> buscarPorId(Long id) {
        return docenteRepository.findById(id);
    }

    public Docente guardar(Docente docente) {
        return docenteRepository.save(docente);
    }

    public void eliminar(Long id) {
        docenteRepository.deleteById(id);
    }

    public List<Docente> buscarPorFacultad(Long facultadId) {
        return docenteRepository.findByFacultadId(facultadId);
    }

    public boolean existeCedula(String cedula) {
        return docenteRepository.existsByCedula(cedula);
    }
}