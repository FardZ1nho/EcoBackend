package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDateTime;

public class SoporteSolicitudDTO {
    private int idSoporteSolicitud;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechahora;
    private String apartado; // ✅ CAMBIAR A MINÚSCULA para consistencia
    private int idUsuario;

    // Constructor vacío
    public SoporteSolicitudDTO() {
    }

    // Constructor completo CORREGIDO
    public SoporteSolicitudDTO(int idSoporteSolicitud, int idUsuario, String apartado, LocalDateTime fechahora, String descripcion, String titulo) {
        this.idSoporteSolicitud = idSoporteSolicitud;
        this.idUsuario = idUsuario;
        this.apartado = apartado; // ✅ CORREGIDO
        this.fechahora = fechahora;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    // Getters y Setters
    public int getIdSoporteSolicitud() {
        return idSoporteSolicitud;
    }

    public void setIdSoporteSolicitud(int idSoporteSolicitud) {
        this.idSoporteSolicitud = idSoporteSolicitud;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApartado() {
        return apartado;
    }

    public void setApartado(String apartado) {
        this.apartado = apartado;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}