package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;

public class UsuarioRecompensaDTO {
    private int idUsuarioRecompensa;
    private int idUsuario;
    private int idRecompensa;
    private LocalDate fechaAsignacion;

    // Constructores
    public UsuarioRecompensaDTO() {
    }

    public UsuarioRecompensaDTO(int idUsuarioRecompensa, int idUsuario, int idRecompensa, LocalDate fechaAsignacion) {
        this.idUsuarioRecompensa = idUsuarioRecompensa;
        this.idUsuario = idUsuario;
        this.idRecompensa = idRecompensa;
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getters y Setters
    public int getIdUsuarioRecompensa() {
        return idUsuarioRecompensa;
    }

    public void setIdUsuarioRecompensa(int idUsuarioRecompensa) {
        this.idUsuarioRecompensa = idUsuarioRecompensa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRecompensa() {
        return idRecompensa;
    }

    public void setIdRecompensa(int idRecompensa) {
        this.idRecompensa = idRecompensa;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}