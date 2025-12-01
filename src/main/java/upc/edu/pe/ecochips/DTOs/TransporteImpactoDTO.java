package upc.edu.pe.ecochips.DTOs;

public class TransporteImpactoDTO {
    private String nombre;
    private Double factorCo2;

    public TransporteImpactoDTO(String nombre, Double factorCo2) {
        this.nombre = nombre;
        this.factorCo2 = factorCo2;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getFactorCo2() { return factorCo2; }
    public void setFactorCo2(Double factorCo2) { this.factorCo2 = factorCo2; }
}
