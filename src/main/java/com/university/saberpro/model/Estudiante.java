package com.university.saberpro.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false, unique = true)
    private String numeroDocumento;

    @Column(nullable = false)
    private String primerApellido;

    private String segundoApellido;

    @Column(nullable = false)
    private String primerNombre;

    private String segundoNombre;

    @Column(unique = true)
    private String correo;

    private String telefono;

    private boolean aprobadoSaberPro = false;

    private String reciboPagoNombre;

    private String reciboPagoTipo;

    private LocalDateTime fechaCargueRecibo;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] reciboPagoArchivo;

    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<ResultadoSaberPro> resultados;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public boolean isAprobadoSaberPro() { return aprobadoSaberPro; }
    public void setAprobadoSaberPro(boolean aprobadoSaberPro) { this.aprobadoSaberPro = aprobadoSaberPro; }
    public String getReciboPagoNombre() { return reciboPagoNombre; }
    public void setReciboPagoNombre(String reciboPagoNombre) { this.reciboPagoNombre = reciboPagoNombre; }
    public String getReciboPagoTipo() { return reciboPagoTipo; }
    public void setReciboPagoTipo(String reciboPagoTipo) { this.reciboPagoTipo = reciboPagoTipo; }
    public LocalDateTime getFechaCargueRecibo() { return fechaCargueRecibo; }
    public void setFechaCargueRecibo(LocalDateTime fechaCargueRecibo) { this.fechaCargueRecibo = fechaCargueRecibo; }
    public byte[] getReciboPagoArchivo() { return reciboPagoArchivo; }
    public void setReciboPagoArchivo(byte[] reciboPagoArchivo) { this.reciboPagoArchivo = reciboPagoArchivo; }
    public boolean tieneReciboPago() { return reciboPagoArchivo != null && reciboPagoArchivo.length > 0; }
    public Facultad getFacultad() { return facultad; }
    public void setFacultad(Facultad facultad) { this.facultad = facultad; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<ResultadoSaberPro> getResultados() { return resultados; }
    public void setResultados(List<ResultadoSaberPro> resultados) { this.resultados = resultados; }
}