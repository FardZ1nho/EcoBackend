package upc.edu.pe.ecochips.Entities;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ParticipacionReto")
public class ParticipacionReto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipacion;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idReto", nullable = false)
    private Reto reto;

    @Column(name = "completado", nullable = false)
    private boolean completado;

    @Column(name = "fecha_participacion")
    private LocalDateTime fechaParticipacion;

    public ParticipacionReto() {}

    public ParticipacionReto(int idParticipacion, LocalDateTime fechaParticipacion, boolean completado, Reto reto, Usuario usuario) {
        this.idParticipacion = idParticipacion;
        this.fechaParticipacion = fechaParticipacion;
        this.completado = completado;
        this.reto = reto;
        this.usuario = usuario;
    }

    public LocalDateTime getFechaParticipacion() {
        return fechaParticipacion;
    }

    public void setFechaParticipacion(LocalDateTime fechaParticipacion) {
        this.fechaParticipacion = fechaParticipacion;
    }

    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Reto getReto() {
        return reto;
    }

    public void setReto(Reto reto) {
        this.reto = reto;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}