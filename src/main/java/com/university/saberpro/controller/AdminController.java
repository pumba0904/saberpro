package com.university.saberpro.controller;

import com.university.saberpro.model.Director;
import com.university.saberpro.model.Docente;
import com.university.saberpro.model.Facultad;
import com.university.saberpro.model.Usuario;
import com.university.saberpro.service.DirectorService;
import com.university.saberpro.service.DocenteService;
import com.university.saberpro.service.FacultadService;
import com.university.saberpro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FacultadService facultadService;
    @Autowired
    private DocenteService docenteService;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalFacultades", facultadService.listarTodas().size());
        model.addAttribute("totalDocentes", docenteService.listarTodos().size());
        model.addAttribute("totalDirectores", directorService.listarTodos().size());
        return "admin/dashboard";
    }

    // ===== FACULTADES =====
    @GetMapping("/facultades")
    public String listarFacultades(Model model) {
        model.addAttribute("facultades", facultadService.listarTodas());
        return "admin/facultades";
    }

    @GetMapping("/facultades/nueva")
    public String nuevaFacultad(Model model) {
        model.addAttribute("facultad", new Facultad());
        return "admin/facultad-form";
    }

    @PostMapping("/facultades/guardar")
    public String guardarFacultad(@ModelAttribute Facultad facultad, RedirectAttributes ra) {
        facultadService.guardar(facultad);
        ra.addFlashAttribute("mensaje", "Facultad guardada correctamente");
        return "redirect:/admin/facultades";
    }

    @GetMapping("/facultades/editar/{id}")
    public String editarFacultad(@PathVariable Long id, Model model) {
        facultadService.buscarPorId(id).ifPresent(f -> model.addAttribute("facultad", f));
        return "admin/facultad-form";
    }

    @GetMapping("/facultades/eliminar/{id}")
    public String eliminarFacultad(@PathVariable Long id, RedirectAttributes ra) {
        facultadService.eliminar(id);
        ra.addFlashAttribute("mensaje", "Facultad eliminada correctamente");
        return "redirect:/admin/facultades";
    }

    // ===== DOCENTES =====
    @GetMapping("/docentes")
    public String listarDocentes(Model model) {
        model.addAttribute("docentes", docenteService.listarTodos());
        return "admin/docentes";
    }

    @GetMapping("/docentes/nuevo")
    public String nuevoDocente(Model model) {
        model.addAttribute("docente", new Docente());
        model.addAttribute("facultades", facultadService.listarTodas());
        return "admin/docente-form";
    }

    @PostMapping("/docentes/guardar")
    public String guardarDocente(@ModelAttribute Docente docente,
                                  @RequestParam(required = false) Long facultadId,
                                  @RequestParam(required = false) String password,
                                  RedirectAttributes ra) {
        if (facultadId != null) {
            facultadService.buscarPorId(facultadId).ifPresent(docente::setFacultad);
        }
        docenteService.guardar(docente);

        if (password != null && !password.isEmpty() && !usuarioService.existeUsername(docente.getCedula())) {
            Usuario usuario = new Usuario();
            usuario.setUsername(docente.getCedula());
            usuario.setPassword(passwordEncoder.encode(password));
            usuario.setNombre(docente.getNombre());
            usuario.setEmail(docente.getCorreo() != null ? docente.getCorreo() : docente.getCedula() + "@saberpro.com");
            usuario.setRol(Usuario.Rol.DOCENTE);
            usuario.setActivo(true);
            usuarioService.guardar(usuario);
        }

        ra.addFlashAttribute("mensaje", "Docente guardado correctamente");
        return "redirect:/admin/docentes";
    }

    @GetMapping("/docentes/editar/{id}")
    public String editarDocente(@PathVariable Long id, Model model) {
        docenteService.buscarPorId(id).ifPresent(d -> model.addAttribute("docente", d));
        model.addAttribute("facultades", facultadService.listarTodas());
        return "admin/docente-form";
    }

    @GetMapping("/docentes/eliminar/{id}")
    public String eliminarDocente(@PathVariable Long id, RedirectAttributes ra) {
        docenteService.eliminar(id);
        ra.addFlashAttribute("mensaje", "Docente eliminado correctamente");
        return "redirect:/admin/docentes";
    }

    // ===== DIRECTORES =====
    @GetMapping("/directores")
    public String listarDirectores(Model model) {
        model.addAttribute("directores", directorService.listarTodos());
        return "admin/directores";
    }

    @GetMapping("/directores/nuevo")
    public String nuevoDirector(Model model) {
        model.addAttribute("director", new Director());
        model.addAttribute("facultades", facultadService.listarTodas());
        return "admin/director-form";
    }

    @PostMapping("/directores/guardar")
    public String guardarDirector(@ModelAttribute Director director,
                                   @RequestParam(required = false) Long facultadId,
                                   RedirectAttributes ra) {
        if (facultadId != null) {
            facultadService.buscarPorId(facultadId).ifPresent(director::setFacultad);
        }
        directorService.guardar(director);
        ra.addFlashAttribute("mensaje", "Director guardado correctamente");
        return "redirect:/admin/directores";
    }

    @GetMapping("/directores/editar/{id}")
    public String editarDirector(@PathVariable Long id, Model model) {
        directorService.buscarPorId(id).ifPresent(d -> model.addAttribute("director", d));
        model.addAttribute("facultades", facultadService.listarTodas());
        return "admin/director-form";
    }

    @GetMapping("/directores/eliminar/{id}")
    public String eliminarDirector(@PathVariable Long id, RedirectAttributes ra) {
        directorService.eliminar(id);
        ra.addFlashAttribute("mensaje", "Director eliminado correctamente");
        return "redirect:/admin/directores";
    }
}