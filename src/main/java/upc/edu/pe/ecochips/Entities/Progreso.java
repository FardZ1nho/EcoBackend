package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "progreso")
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProgreso;

    // CORREGIR: Cambiar Integer por relación ManyToOne
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    @Column(name = "estado", length = 50, nullable = false)
    private String estado;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    // Constructores
    public Progreso() {
        this.puntos = 0;
        this.estado = "Activo";
        this.fecha = LocalDate.now();
    }

    public Progreso(Integer idProgreso, Usuario usuario, Integer puntos, String estado, LocalDate fecha) {
        this.idProgreso = idProgreso;
        this.usuario = usuario;
        this.puntos = puntos;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getIdProgreso() {
        return idProgreso;
    }

    public void setIdProgreso(Integer idProgreso) {
        this.idProgreso = idProgreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}