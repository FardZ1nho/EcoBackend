package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvento;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "direccion", length = 200, nullable = false)
    private String direccion;

    public Evento() {
    }

    public Evento(int idEvento, String titulo, String descripcion, LocalDate fecha,
                  LocalTime hora, String direccion) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
    }

    // Getters y Setters
    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}