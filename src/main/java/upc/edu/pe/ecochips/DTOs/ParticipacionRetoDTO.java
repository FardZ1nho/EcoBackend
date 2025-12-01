package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDateTime;

public class ParticipacionRetoDTO {
    private int idParticipacion;
    private int idUsuario;
    private int idReto;
    private boolean completado;
    private LocalDateTime fechaParticipacion; // Opcional: para tracking
    private String nombreUsuario; // Opcional: para mostrar en frontend
    private String tituloReto; // Opcional: para mostrar en frontend

    // Constructores
    public ParticipacionRetoDTO() {
    }

    public ParticipacionRetoDTO(int idParticipacion, int idUsuario, int idReto, boolean completado) {
        this.idParticipacion = idParticipacion;
        this.idUsuario = idUsuario;
        this.idReto = idReto;
        this.completado = completado;
    }

    // Constructor completo
    public ParticipacionRetoDTO(int idParticipacion, int idUsuario, int idReto, boolean completado,
                                LocalDateTime fechaParticipacion, String nombreUsuario, String tituloReto) {
        this.idParticipacion = idParticipacion;
        this.idUsuario = idUsuario;
        this.idReto = idReto;
        this.completado = completado;
        this.fechaParticipacion = fechaParticipacion;
        this.nombreUsuario = nombreUsuario;
        this.tituloReto = tituloReto;
    }

    // Getters y Setters
    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdReto() {
        return idReto;
    }

    public void setIdReto(int idReto) {
        this.idReto = idReto;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public LocalDateTime getFechaParticipacion() {
        return fechaParticipacion;
    }

    public void setFechaParticipacion(LocalDateTime fechaParticipacion) {
        this.fechaParticipacion = fechaParticipacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTituloReto() {
        return tituloReto;
    }

    public void setTituloReto(String tituloReto) {
        this.tituloReto = tituloReto;
    }
}