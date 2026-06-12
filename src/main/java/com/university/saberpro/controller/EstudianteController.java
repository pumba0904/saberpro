package com.university.saberpro.controller;

import com.university.saberpro.model.Estudiante;
import com.university.saberpro.repository.EstudianteRepository;
import com.university.saberpro.service.ResultadoSaberProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ResultadoSaberProService resultadoService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        estudianteRepository.findByNumeroDocumento(auth.getName()).ifPresent(e -> {
            model.addAttribute("estudiante", e);
            resultadoService.ultimoResultado(e.getId())
                    .ifPresent(r -> model.addAttribute("ultimoResultado", r));
        });
        return "estudiante/dashboard";
    }

    @GetMapping("/perfil")
    public String perfil(Authentication auth, Model model) {
        estudianteRepository.findByNumeroDocumento(auth.getName())
                .ifPresent(e -> model.addAttribute("estudiante", e));
        return "estudiante/perfil";
    }

    @GetMapping("/resultados")
    public String todosResultados(Authentication auth, Model model) {
        estudianteRepository.findByNumeroDocumento(auth.getName()).ifPresent(e -> {
            model.addAttribute("estudiante", e);
            model.addAttribute("resultados", resultadoService.listarPorEstudiante(e.getId()));
        });
        return "estudiante/resultados";
    }

    @GetMapping("/resultado/ultimo")
    public String ultimoResultado(Authentication auth, Model model) {
        estudianteRepository.findByNumeroDocumento(auth.getName()).ifPresent(e -> {
            model.addAttribute("estudiante", e);
            resultadoService.ultimoResultado(e.getId())
                    .ifPresent(r -> model.addAttribute("resultado", r));
        });
        return "estudiante/ultimo-resultado";
    }
}