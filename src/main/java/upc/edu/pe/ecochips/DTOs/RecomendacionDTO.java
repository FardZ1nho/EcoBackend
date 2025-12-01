package upc.edu.pe.ecochips.DTOs;

public class RecomendacionDTO {
    private int idRecomendacion;
    private String titulo;
    private String descripcion;
    private String tipo;

    // Constructores
    public RecomendacionDTO() {
    }

    public RecomendacionDTO(int idRecomendacion, String titulo, String descripcion, String tipo) {
        this.idRecomendacion = idRecomendacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getIdRecomendacion() {
        return idRecomendacion;
    }

    public void setIdRecomendacion(int idRecomendacion) {
        this.idRecomendacion = idRecomendacion;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}