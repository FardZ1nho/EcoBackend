package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;

public class RetoDTO {
    private int idReto;
    private String titulo;
    private String descripcion;
    private double objetivoKg;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Constructores
    public RetoDTO() {
    }

    public RetoDTO(int idReto, String titulo, String descripcion, double objetivoKg,
                   LocalDate fechaInicio, LocalDate fechaFin) {
        this.idReto = idReto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.objetivoKg = objetivoKg;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public int getIdReto() {
        return idReto;
    }

    public void setIdReto(int idReto) {
        this.idReto = idReto;
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

    public double getObjetivoKg() {
        return objetivoKg;
    }

    public void setObjetivoKg(double objetivoKg) {
        this.objetivoKg = objetivoKg;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}