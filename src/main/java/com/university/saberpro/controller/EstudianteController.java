package com.university.saberpro.controller;

import com.university.saberpro.model.Estudiante;
import com.university.saberpro.repository.EstudianteRepository;
import com.university.saberpro.service.ResultadoSaberProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.time.LocalDateTime;

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

    @GetMapping("/pago")
    public String pago(Authentication auth, Model model) {
        estudianteRepository.findByNumeroDocumento(auth.getName())
                .ifPresent(e -> model.addAttribute("estudiante", e));
        return "estudiante/pago";
    }

    @PostMapping("/pago/subir")
    public String subirPago(Authentication auth,
                            @RequestParam("archivo") MultipartFile archivo,
                            RedirectAttributes ra) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            ra.addFlashAttribute("error", "Selecciona un archivo PDF o imagen para subir el recibo.");
            return "redirect:/estudiante/pago";
        }

        String contentType = archivo.getContentType();
        boolean permitido = contentType != null && (contentType.equals("application/pdf") || contentType.startsWith("image/"));
        if (!permitido) {
            ra.addFlashAttribute("error", "Solo se permiten archivos PDF o imágenes.");
            return "redirect:/estudiante/pago";
        }

        estudianteRepository.findByNumeroDocumento(auth.getName()).ifPresent(e -> {
            try {
                e.setReciboPagoNombre(archivo.getOriginalFilename());
                e.setReciboPagoTipo(contentType);
                e.setFechaCargueRecibo(LocalDateTime.now());
                e.setReciboPagoArchivo(archivo.getBytes());
                e.setAprobadoSaberPro(false);
                estudianteRepository.save(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        ra.addFlashAttribute("mensaje", "Recibo de pago cargado correctamente. Coordinación debe revisarlo y aprobarlo.");
        return "redirect:/estudiante/pago";
    }

    @GetMapping("/pago/descargar")
    public ResponseEntity<byte[]> descargarPago(Authentication auth) {
        Estudiante estudiante = estudianteRepository.findByNumeroDocumento(auth.getName()).orElse(null);
        if (estudiante == null || !estudiante.tieneReciboPago()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + estudiante.getReciboPagoNombre() + "\"")
                .contentType(MediaType.parseMediaType(estudiante.getReciboPagoTipo()))
                .body(estudiante.getReciboPagoArchivo());
    }

}