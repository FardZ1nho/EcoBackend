package upc.edu.pe.ecochips.DTOs;

public class RecomendacionTipoDTO {
    private String tipo;
    private Long cantidad;

    public RecomendacionTipoDTO(String tipo, Long cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }
}