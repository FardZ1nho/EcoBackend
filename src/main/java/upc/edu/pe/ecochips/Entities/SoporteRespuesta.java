package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SoporteRespuesta")
public class SoporteRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRespuesta;

    @Column(name = "respuesta", length = 255, nullable = false)
    private String respuesta;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechahora;

    @ManyToOne
    @JoinColumn(name = "idSolicitud", nullable = false)
    private SoporteSolicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRespuesta", nullable = false)
    private Usuario usuarioRespuesta;

    public SoporteRespuesta() {}

    public SoporteRespuesta(int idRespuesta, Usuario usuarioRespuesta, SoporteSolicitud solicitud, LocalDateTime fechahora, String respuesta) {
        this.idRespuesta = idRespuesta;
        this.usuarioRespuesta = usuarioRespuesta;
        this.solicitud = solicitud;
        this.fechahora = fechahora;
        this.respuesta = respuesta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Usuario getUsuarioRespuesta() {
        return usuarioRespuesta;
    }

    public void setUsuarioRespuesta(Usuario usuarioRespuesta) {
        this.usuarioRespuesta = usuarioRespuesta;
    }

    public SoporteSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SoporteSolicitud solicitud) {
        this.solicitud = solicitud;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
