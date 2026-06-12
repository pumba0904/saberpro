package com.university.saberpro.controller;

import com.university.saberpro.service.EstudianteService;
import com.university.saberpro.service.FacultadService;
import com.university.saberpro.service.ResultadoSaberProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private ResultadoSaberProService resultadoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalEstudiantes", estudianteService.listarTodos().size());
        model.addAttribute("totalFacultades", facultadService.listarTodas().size());
        long aprobados = estudianteService.listarTodos()
                .stream().filter(e -> e.isAprobadoSaberPro()).count();
        model.addAttribute("totalAprobados", aprobados);
        return "docente/dashboard";
    }

    // Consultar todos los estudiantes
    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        model.addAttribute("facultades", facultadService.listarTodas());
        return "docente/estudiantes";
    }

    // Buscar por cédula
    @GetMapping("/estudiantes/buscar")
    public String buscarPorCedula(@RequestParam(required = false) String cedula,
                                   @RequestParam(required = false) Long facultadId,
                                   Model model) {
        if (cedula != null && !cedula.isEmpty()) {
            estudianteService.buscarPorDocumento(cedula)
                    .ifPresent(e -> model.addAttribute("estudianteEncontrado", e));
        }
        if (facultadId != null) {
            model.addAttribute("estudiantesFacultad",
                    estudianteService.listarPorFacultad(facultadId));
        }
        model.addAttribute("facultades", facultadService.listarTodas());
        model.addAttribute("cedula", cedula);
        model.addAttribute("facultadId", facultadId);
        return "docente/buscar";
    }

    // Ver resultados de un estudiante
    @GetMapping("/estudiantes/{id}/resultados")
    public String verResultados(@PathVariable Long id, Model model) {
        estudianteService.buscarPorId(id).ifPresent(e -> {
            model.addAttribute("estudiante", e);
            model.addAttribute("resultados", resultadoService.listarPorEstudiante(id));
            resultadoService.ultimoResultado(id)
                    .ifPresent(r -> model.addAttribute("ultimoResultado", r));
        });
        return "docente/resultados";
    }
}