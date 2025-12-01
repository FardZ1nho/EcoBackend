package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SoporteSolicitud")
public class SoporteSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSoporteSolicitud;

    @Column(name="titulo", length = 100, nullable = false)
    private String titulo;

    // ✅ CAMBIO: Aumentado a 1000 caracteres para permitir descripciones largas
    @Column(name="descripcion", length = 1000, nullable = false)
    private String descripcion;

    @Column(name="fecha_hora", nullable = false)
    private LocalDateTime fechahora;

    @Column(name="Apartado", length = 50, nullable = false)
    private String Apartado;

    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    public SoporteSolicitud() {}

    public SoporteSolicitud(int idSoporteSolicitud, Usuario usuario, String Apartado, LocalDateTime fechahora, String titulo, String descripcion) {
        this.idSoporteSolicitud = idSoporteSolicitud;
        this.usuario = usuario;
        this.Apartado = Apartado;
        this.fechahora = fechahora;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getIdSoporteSolicitud() {
        return idSoporteSolicitud;
    }

    public void setIdSoporteSolicitud(int idSoporteSolicitud) {
        this.idSoporteSolicitud = idSoporteSolicitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getApartado() {
        return Apartado;
    }

    public void setApartado(String Apartado) {
        this.Apartado = Apartado;
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