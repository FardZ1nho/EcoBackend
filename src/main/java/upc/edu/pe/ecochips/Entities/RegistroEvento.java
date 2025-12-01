package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name ="RegistroEvento")
public class RegistroEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRegistroEvento;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    @Column(name = "fechaRegistro", nullable = false)
    private LocalDate fechaRegistro;

    public RegistroEvento() {}

    public RegistroEvento(int idRegistroEvento, Evento evento, LocalDate fechaRegistro, Usuario usuario) {
        this.idRegistroEvento = idRegistroEvento;
        this.evento = evento;
        this.fechaRegistro = fechaRegistro;
        this.usuario = usuario;
    }

    public int getIdRegistroEvento() {
        return idRegistroEvento;
    }

    public void setIdRegistroEvento(int idRegistroEvento) {
        this.idRegistroEvento = idRegistroEvento;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
