package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;

public class UsuarioRecomendacionDTO {
    private int idUsuarioRecomendacion;
    private int idUsuario;
    private int idRecomendacion;
    private LocalDate fechaAsignacion;

    // Constructores
    public UsuarioRecomendacionDTO() {
    }

    public UsuarioRecomendacionDTO(int idUsuarioRecomendacion, int idUsuario, int idRecomendacion, LocalDate fechaAsignacion) {
        this.idUsuarioRecomendacion = idUsuarioRecomendacion;
        this.idUsuario = idUsuario;
        this.idRecomendacion = idRecomendacion;
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getters y Setters
    public int getIdUsuarioRecomendacion() {
        return idUsuarioRecomendacion;
    }

    public void setIdUsuarioRecomendacion(int idUsuarioRecomendacion) {
        this.idUsuarioRecomendacion = idUsuarioRecomendacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRecomendacion() {
        return idRecomendacion;
    }

    public void setIdRecomendacion(int idRecomendacion) {
        this.idRecomendacion = idRecomendacion;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}