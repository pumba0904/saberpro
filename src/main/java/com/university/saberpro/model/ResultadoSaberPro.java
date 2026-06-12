package com.university.saberpro.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resultados_saber_pro")
public class ResultadoSaberPro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroRegistro;

    private LocalDate fechaCargue;

    private Double puntajeTotal;
    private String nivelGlobal;

    private Double comunicacionEscrita;
    private String comunicacionEscritaNivel;

    private Double razonamientoCuantitativo;
    private String razonamientoCuantitativoNivel;

    private Double lecturaCritica;
    private String lecturaCriticaNivel;

    private Double competenciasCiudadanas;
    private String competenciasCiudadanasNivel;

    private Double ingles;
    private String inglesNivel;
    private String inglesNivelDetalle;

    private Double formulacionProyectos;
    private String formulacionProyectosNivel;

    private Double pensamientoCientifico;
    private String pensamientoCientificoNivel;

    private Double disenoSoftware;
    private String disenoSoftwareNivel;

    private String comprobantePago;
    private boolean pagoCargado = false;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    public LocalDate getFechaCargue() { return fechaCargue; }
    public void setFechaCargue(LocalDate fechaCargue) { this.fechaCargue = fechaCargue; }
    public Double getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(Double puntajeTotal) { this.puntajeTotal = puntajeTotal; }
    public String getNivelGlobal() { return nivelGlobal; }
    public void setNivelGlobal(String nivelGlobal) { this.nivelGlobal = nivelGlobal; }
    public Double getComunicacionEscrita() { return comunicacionEscrita; }
    public void setComunicacionEscrita(Double comunicacionEscrita) { this.comunicacionEscrita = comunicacionEscrita; }
    public String getComunicacionEscritaNivel() { return comunicacionEscritaNivel; }
    public void setComunicacionEscritaNivel(String comunicacionEscritaNivel) { this.comunicacionEscritaNivel = comunicacionEscritaNivel; }
    public Double getRazonamientoCuantitativo() { return razonamientoCuantitativo; }
    public void setRazonamientoCuantitativo(Double razonamientoCuantitativo) { this.razonamientoCuantitativo = razonamientoCuantitativo; }
    public String getRazonamientoCuantitativoNivel() { return razonamientoCuantitativoNivel; }
    public void setRazonamientoCuantitativoNivel(String razonamientoCuantitativoNivel) { this.razonamientoCuantitativoNivel = razonamientoCuantitativoNivel; }
    public Double getLecturaCritica() { return lecturaCritica; }
    public void setLecturaCritica(Double lecturaCritica) { this.lecturaCritica = lecturaCritica; }
    public String getLecturaCriticaNivel() { return lecturaCriticaNivel; }
    public void setLecturaCriticaNivel(String lecturaCriticaNivel) { this.lecturaCriticaNivel = lecturaCriticaNivel; }
    public Double getCompetenciasCiudadanas() { return competenciasCiudadanas; }
    public void setCompetenciasCiudadanas(Double competenciasCiudadanas) { this.competenciasCiudadanas = competenciasCiudadanas; }
    public String getCompetenciasCiudadanasNivel() { return competenciasCiudadanasNivel; }
    public void setCompetenciasCiudadanasNivel(String competenciasCiudadanasNivel) { this.competenciasCiudadanasNivel = competenciasCiudadanasNivel; }
    public Double getIngles() { return ingles; }
    public void setIngles(Double ingles) { this.ingles = ingles; }
    public String getInglesNivel() { return inglesNivel; }
    public void setInglesNivel(String inglesNivel) { this.inglesNivel = inglesNivel; }
    public String getInglesNivelDetalle() { return inglesNivelDetalle; }
    public void setInglesNivelDetalle(String inglesNivelDetalle) { this.inglesNivelDetalle = inglesNivelDetalle; }
    public Double getFormulacionProyectos() { return formulacionProyectos; }
    public void setFormulacionProyectos(Double formulacionProyectos) { this.formulacionProyectos = formulacionProyectos; }
    public String getFormulacionProyectosNivel() { return formulacionProyectosNivel; }
    public void setFormulacionProyectosNivel(String formulacionProyectosNivel) { this.formulacionProyectosNivel = formulacionProyectosNivel; }
    public Double getPensamientoCientifico() { return pensamientoCientifico; }
    public void setPensamientoCientifico(Double pensamientoCientifico) { this.pensamientoCientifico = pensamientoCientifico; }
    public String getPensamientoCientificoNivel() { return pensamientoCientificoNivel; }
    public void setPensamientoCientificoNivel(String pensamientoCientificoNivel) { this.pensamientoCientificoNivel = pensamientoCientificoNivel; }
    public Double getDisenoSoftware() { return disenoSoftware; }
    public void setDisenoSoftware(Double disenoSoftware) { this.disenoSoftware = disenoSoftware; }
    public String getDisenoSoftwareNivel() { return disenoSoftwareNivel; }
    public void setDisenoSoftwareNivel(String disenoSoftwareNivel) { this.disenoSoftwareNivel = disenoSoftwareNivel; }
    public String getComprobantePago() { return comprobantePago; }
    public void setComprobantePago(String comprobantePago) { this.comprobantePago = comprobantePago; }
    public boolean isPagoCargado() { return pagoCargado; }
    public void setPagoCargado(boolean pagoCargado) { this.pagoCargado = pagoCargado; }
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
}