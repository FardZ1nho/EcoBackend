package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;

public class ProgresoDTO {
    private Integer idProgreso;
    private Integer idUsuario;
    private Integer puntos;
    private String estado;
    private LocalDate fecha;

    // Constructores
    public ProgresoDTO() {
    }

    public ProgresoDTO(Integer idProgreso, Integer idUsuario, Integer puntos, String estado, LocalDate fecha) {
        this.idProgreso = idProgreso;
        this.idUsuario = idUsuario;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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