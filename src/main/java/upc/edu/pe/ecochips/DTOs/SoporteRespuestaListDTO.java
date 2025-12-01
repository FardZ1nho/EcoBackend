package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SoporteRespuestaListDTO {
    private int idRespuesta;
    private String respuesta;
    private LocalDateTime fechahora;

    // Información del usuario que RESPONDE
    private String nombreUsuarioRespuesta;
    private String correoUsuarioRespuesta;

    // Información de la solicitud
    private String tituloSolicitud;
    private String descripcionSolicitud;
    private String estadoSolicitud;

    // Información del usuario que PREGUNTA (opcional)
    private String nombreUsuarioSolicitud;
    private String correoUsuarioSolicitud;

    // Getters y Setters
    public int getIdRespuesta() {return idRespuesta;}
    public void setIdRespuesta(int idRespuesta) {this.idRespuesta = idRespuesta;}
    public String getRespuesta() {return respuesta;}
    public void setRespuesta(String respuesta) {this.respuesta = respuesta;}

    public LocalDateTime getFechahora() {return fechahora;}

    public void setFechahora(LocalDateTime fechahora) {this.fechahora = fechahora;}

    public String getNombreUsuarioRespuesta() {return nombreUsuarioRespuesta;} // ✅
    public void setNombreUsuarioRespuesta(String nombreUsuarioRespuesta) {this.nombreUsuarioRespuesta = nombreUsuarioRespuesta;} // ✅
    public String getCorreoUsuarioRespuesta() {return correoUsuarioRespuesta;} // ✅
    public void setCorreoUsuarioRespuesta(String correoUsuarioRespuesta) {this.correoUsuarioRespuesta = correoUsuarioRespuesta;} // ✅

    public String getTituloSolicitud() {return tituloSolicitud;}
    public void setTituloSolicitud(String tituloSolicitud) {this.tituloSolicitud = tituloSolicitud;}
    public String getDescripcionSolicitud() {return descripcionSolicitud;}
    public void setDescripcionSolicitud(String descripcionSolicitud) {this.descripcionSolicitud = descripcionSolicitud;}
    public String getEstadoSolicitud() {return estadoSolicitud;}
    public void setEstadoSolicitud(String estadoSolicitud) {this.estadoSolicitud = estadoSolicitud;}

    public String getNombreUsuarioSolicitud() {return nombreUsuarioSolicitud;} // ✅
    public void setNombreUsuarioSolicitud(String nombreUsuarioSolicitud) {this.nombreUsuarioSolicitud = nombreUsuarioSolicitud;} // ✅
    public String getCorreoUsuarioSolicitud() {return correoUsuarioSolicitud;} // ✅
    public void setCorreoUsuarioSolicitud(String correoUsuarioSolicitud) {this.correoUsuarioSolicitud = correoUsuarioSolicitud;} // ✅
}