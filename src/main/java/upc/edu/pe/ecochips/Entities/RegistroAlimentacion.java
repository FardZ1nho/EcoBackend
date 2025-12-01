package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registro_alimentacion")
public class RegistroAlimentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistroAlimentacion;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_alimento", nullable = false)
    private Alimento alimento;

    @Column(name = "porciones", nullable = false)
    private Integer porciones;

    @Column(name = "co2_emitido", nullable = false)
    private Double co2Emitido;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    public RegistroAlimentacion() {
    }

    public RegistroAlimentacion(Integer idRegistroAlimentacion, Usuario usuario, Alimento alimento,
                                Integer porciones, Double co2Emitido, LocalDate fecha) {
        this.idRegistroAlimentacion = idRegistroAlimentacion;
        this.usuario = usuario;
        this.alimento = alimento;
        this.porciones = porciones;
        this.co2Emitido = co2Emitido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getIdRegistroAlimentacion() {
        return idRegistroAlimentacion;
    }

    public void setIdRegistroAlimentacion(Integer idRegistroAlimentacion) {
        this.idRegistroAlimentacion = idRegistroAlimentacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) {
        this.porciones = porciones;
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