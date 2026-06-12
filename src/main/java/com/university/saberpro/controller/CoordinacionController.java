package com.university.saberpro.controller;

import com.university.saberpro.model.Estudiante;
import com.university.saberpro.model.ResultadoSaberPro;
import com.university.saberpro.model.Usuario;
import com.university.saberpro.service.EstudianteService;
import com.university.saberpro.service.FacultadService;
import com.university.saberpro.service.ResultadoSaberProService;
import com.university.saberpro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/coordinacion")
public class CoordinacionController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResultadoSaberProService resultadoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalEstudiantes", estudianteService.listarTodos().size());
        long aprobados = estudianteService.listarTodos().stream().filter(Estudiante::isAprobadoSaberPro).count();
        model.addAttribute("totalAprobados", aprobados);
        return "coordinacion/dashboard";
    }

    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "coordinacion/estudiantes";
    }

    @GetMapping("/estudiantes/nuevo")
    public String nuevoEstudiante(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("facultades", facultadService.listarTodas());
        return "coordinacion/estudiante-form";
    }

    @PostMapping("/estudiantes/guardar")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante,
                                     @RequestParam(required = false) Long facultadId,
                                     @RequestParam(required = false) String password,
                                     RedirectAttributes ra) {
        if (facultadId != null) {
            facultadService.buscarPorId(facultadId).ifPresent(estudiante::setFacultad);
        }

        // Preservar estado aprobado si ya existía
        if (estudiante.getId() != null) {
            estudianteService.buscarPorId(estudiante.getId()).ifPresent(existente -> {
                if (existente.isAprobadoSaberPro()) {
                    estudiante.setAprobadoSaberPro(true);
                }
            });
        }

        Estudiante guardado = estudianteService.guardar(estudiante);

        if (password != null && !password.isEmpty() && !usuarioService.existeUsername(guardado.getNumeroDocumento())) {
            Usuario usuario = new Usuario();
            usuario.setUsername(guardado.getNumeroDocumento());
            usuario.setPassword(passwordEncoder.encode(password));
            usuario.setNombre(guardado.getPrimerNombre() + " " + guardado.getPrimerApellido());
            usuario.setEmail(guardado.getCorreo() != null ? guardado.getCorreo() : guardado.getNumeroDocumento() + "@saberpro.com");
            usuario.setRol(Usuario.Rol.ESTUDIANTE);
            usuarioService.guardar(usuario);
        }

        ra.addFlashAttribute("mensaje", "Estudiante guardado correctamente");
        return "redirect:/coordinacion/estudiantes";
    }

    @GetMapping("/estudiantes/editar/{id}")
    public String editarEstudiante(@PathVariable Long id, Model model) {
        estudianteService.buscarPorId(id).ifPresent(e -> model.addAttribute("estudiante", e));
        model.addAttribute("facultades", facultadService.listarTodas());
        return "coordinacion/estudiante-form";
    }

    @GetMapping("/estudiantes/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id, RedirectAttributes ra) {
        estudianteService.eliminar(id);
        ra.addFlashAttribute("mensaje", "Estudiante eliminado correctamente");
        return "redirect:/coordinacion/estudiantes";
    }

    @GetMapping("/estudiantes/aprobar/{id}")
    public String aprobarEstudiante(@PathVariable Long id, RedirectAttributes ra) {
        estudianteService.buscarPorId(id).ifPresent(e -> {
            e.setAprobadoSaberPro(true);
            estudianteService.guardar(e);
        });
        ra.addFlashAttribute("mensaje", "Estudiante aprobado para Saber Pro");
        return "redirect:/coordinacion/estudiantes";
    }

    // ===== RESULTADOS =====
    @GetMapping("/estudiantes/{id}/resultados")
    public String verResultados(@PathVariable Long id, Model model) {
        estudianteService.buscarPorId(id).ifPresent(e -> {
            model.addAttribute("estudiante", e);
            model.addAttribute("resultados", resultadoService.listarPorEstudiante(id));
        });
        return "coordinacion/resultados";
    }

    @GetMapping("/estudiantes/{id}/resultados/nuevo")
    public String nuevoResultado(@PathVariable Long id, Model model) {
        estudianteService.buscarPorId(id).ifPresent(e -> model.addAttribute("estudiante", e));
        model.addAttribute("resultado", new ResultadoSaberPro());
        return "coordinacion/resultado-form";
    }

    @PostMapping("/estudiantes/{id}/resultados/guardar")
    public String guardarResultado(@PathVariable Long id,
                                    @ModelAttribute ResultadoSaberPro resultado,
                                    RedirectAttributes ra) {
        estudianteService.buscarPorId(id).ifPresent(e -> {
            resultado.setEstudiante(e);
            resultado.setFechaCargue(LocalDate.now());
            resultadoService.guardar(resultado);
        });
        ra.addFlashAttribute("mensaje", "Resultado guardado correctamente");
        return "redirect:/coordinacion/estudiantes/" + id + "/resultados";
    }

    @GetMapping("/estudiantes/{id}/resultados/eliminar/{rid}")
    public String eliminarResultado(@PathVariable Long id, @PathVariable Long rid, RedirectAttributes ra) {
        resultadoService.eliminar(rid);
        ra.addFlashAttribute("mensaje", "Resultado eliminado");
        return "redirect:/coordinacion/estudiantes/" + id + "/resultados";
    }
    
 // ===== INFORMES =====
    @GetMapping("/informes")
    public String informes(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        model.addAttribute("totalEstudiantes", estudianteService.listarTodos().size());
        long aprobados = estudianteService.listarTodos().stream().filter(Estudiante::isAprobadoSaberPro).count();
        model.addAttribute("totalAprobados", aprobados);
        model.addAttribute("totalResultados", resultadoService.listarTodos().size());
        return "coordinacion/informes";
    }

    @GetMapping("/informes/estudiante/{id}")
    public String informeEstudiante(@PathVariable Long id, Model model) {
        estudianteService.buscarPorId(id).ifPresent(e -> {
            model.addAttribute("estudiante", e);
            model.addAttribute("resultados", resultadoService.listarPorEstudiante(id));
            resultadoService.ultimoResultado(id).ifPresent(r -> model.addAttribute("ultimoResultado", r));
        });
        return "coordinacion/informe-estudiante";
    }
}