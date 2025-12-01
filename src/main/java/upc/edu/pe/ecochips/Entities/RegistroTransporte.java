// RegistroTransporte.java
package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_transporte")
public class RegistroTransporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistroTransporte;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_transporte", nullable = false)
    private Transporte transporte;

    @Column(name = "distancia_km", nullable = false)
    private Double distanciaKm;

    @Column(name = "co2_emitido", nullable = false)
    private Double co2Emitido;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    // Constructores
    public RegistroTransporte() {
    }

    public RegistroTransporte(Integer idRegistroTransporte, Usuario usuario, Transporte transporte,
                              Double distanciaKm, Double co2Emitido, LocalDate fecha) {
        this.idRegistroTransporte = idRegistroTransporte;
        this.usuario = usuario;
        this.transporte = transporte;
        this.distanciaKm = distanciaKm;
        this.co2Emitido = co2Emitido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getIdRegistroTransporte() {
        return idRegistroTransporte;
    }

    public void setIdRegistroTransporte(Integer idRegistroTransporte) {
        this.idRegistroTransporte = idRegistroTransporte;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public Double getCo2Emitido() {
        return co2Emitido;
    }

    public void setCo2Emitido(Double co2Emitido) {
        this.co2Emitido = co2Emitido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}