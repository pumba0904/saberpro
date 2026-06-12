package com.university.saberpro;

import com.university.saberpro.model.Usuario;
import com.university.saberpro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Administrador");
            admin.setEmail("admin@saberpro.com");
            admin.setRol(Usuario.Rol.ADMINISTRADOR);
            admin.setActivo(true);
            usuarioRepository.save(admin);
            System.out.println(">>> Usuario admin creado correctamente");
        }
        
        if (!usuarioRepository.existsByUsername("coordinacion")) {
            Usuario coord = new Usuario();
            coord.setUsername("coordinacion");
            coord.setPassword(passwordEncoder.encode("coord123"));
            coord.setNombre("Coordinación");
            coord.setEmail("coordinacion@saberpro.com");
            coord.setRol(Usuario.Rol.COORDINACION);
            coord.setActivo(true);
            usuarioRepository.save(coord);
            System.out.println(">>> Usuario coordinacion creado");
        }
    }
}