package upc.edu.pe.ecochips.DTOs;

public class UsuarioImpactoDTO {
    private String nombre;
    private Double co2Total;
    private Integer nivel;

    public UsuarioImpactoDTO(String nombre, Double co2Total, Integer nivel) {
        this.nombre = nombre;
        this.co2Total = co2Total;
        this.nivel = nivel;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getCo2Total() { return co2Total; }
    public void setCo2Total(Double co2Total) { this.co2Total = co2Total; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
}