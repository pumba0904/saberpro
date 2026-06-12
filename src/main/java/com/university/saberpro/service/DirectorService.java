package com.university.saberpro.service;

import com.university.saberpro.model.Director;
import com.university.saberpro.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    public List<Director> listarTodos() {
        return directorRepository.findAll();
    }

    public Optional<Director> buscarPorId(Long id) {
        return directorRepository.findById(id);
    }

    public Director guardar(Director director) {
        return directorRepository.save(director);
    }

    public void eliminar(Long id) {
        directorRepository.deleteById(id);
    }

    public boolean existeCedula(String cedula) {
        return directorRepository.existsByCedula(cedula);
    }
}