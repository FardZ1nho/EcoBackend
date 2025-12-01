package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SoporteRespuestaDTO {
    private int idRespuesta;
    private String respuesta;
    private LocalDateTime fechahora;
    private int idSolicitud; // ✅ CAMBIADO: Solo el ID, no la entidad completa
    private int idUsuarioRespuesta; // ✅ NUEVO: Para saber quién responde

    // Constructores
    public SoporteRespuestaDTO() {}

    public SoporteRespuestaDTO(int idRespuesta, int idUsuarioRespuesta, int idSolicitud, LocalDateTime fechahora, String respuesta) {
        this.idRespuesta = idRespuesta;
        this.idUsuarioRespuesta = idUsuarioRespuesta;
        this.idSolicitud = idSolicitud;
        this.fechahora = fechahora;
        this.respuesta = respuesta;
    }

    // Getters y Setters
    public int getIdRespuesta() {return idRespuesta;}
    public void setIdRespuesta(int idRespuesta) {this.idRespuesta = idRespuesta;}
    public String getRespuesta() {return respuesta;}
    public void setRespuesta(String respuesta) {this.respuesta = respuesta;}

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public int getIdSolicitud() {return idSolicitud;} // ✅
    public void setIdSolicitud(int idSolicitud) {this.idSolicitud = idSolicitud;} // ✅
    public int getIdUsuarioRespuesta() {return idUsuarioRespuesta;} // ✅
    public void setIdUsuarioRespuesta(int idUsuarioRespuesta) {this.idUsuarioRespuesta = idUsuarioRespuesta;} // ✅
}