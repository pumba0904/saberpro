package com.university.saberpro.service;

import com.university.saberpro.model.ResultadoSaberPro;
import com.university.saberpro.repository.ResultadoSaberProRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResultadoSaberProService {

    @Autowired
    private ResultadoSaberProRepository repository;

    public List<ResultadoSaberPro> listarTodos() {
        return repository.findAll();
    }

    public List<ResultadoSaberPro> listarPorEstudiante(Long estudianteId) {
        return repository.findByEstudianteId(estudianteId);
    }

    public Optional<ResultadoSaberPro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public ResultadoSaberPro guardar(ResultadoSaberPro resultado) {
        resultado.setId(null); // siempre insertar nuevo, nunca actualizar
        return repository.save(resultado);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Optional<ResultadoSaberPro> ultimoResultado(Long estudianteId) {
        return repository.findTopByEstudianteIdOrderByFechaCargueDesc(estudianteId);
    }
}