package upc.edu.pe.ecochips.DTOs;

public class TransporteDTO {
    private int idTransporte;
    private String nombre;
    private Double factorCo2;

    // Constructores
    public TransporteDTO() {
    }

    public TransporteDTO(int idTransporte, String nombre, Double factorCo2) {
        this.idTransporte = idTransporte;
        this.nombre = nombre;
        this.factorCo2 = factorCo2;
    }

    // Getters y Setters
    public int getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(int idTransporte) {
        this.idTransporte = idTransporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getFactorCo2() {
        return factorCo2;
    }

    public void setFactorCo2(Double factorCo2) {
        this.factorCo2 = factorCo2;
    }
}