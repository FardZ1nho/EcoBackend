package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoDTO {
    private int idEvento;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private String direccion;

    // Constructores
    public EventoDTO() {
    }

    public EventoDTO(int idEvento, String titulo, String descripcion, LocalDate fecha,
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