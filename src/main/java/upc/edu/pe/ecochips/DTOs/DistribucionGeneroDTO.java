package upc.edu.pe.ecochips.DTOs;

public class DistribucionGeneroDTO {
    private String genero;
    private Long cantidadParticipantes;
    private Double edadPromedio;

    // Constructor vacío
    public DistribucionGeneroDTO() {
    }

    // Constructor con parámetros (para el mapper)
    public DistribucionGeneroDTO(String genero, Long cantidadParticipantes, Double edadPromedio) {
        this.genero = genero;
        this.cantidadParticipantes = cantidadParticipantes;
        this.edadPromedio = edadPromedio;
    }

    // Getters y Setters
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getCantidadParticipantes() {
        return cantidadParticipantes;
    }

    public void setCantidadParticipantes(Long cantidadParticipantes) {
        this.cantidadParticipantes = cantidadParticipantes;
    }

    public Double getEdadPromedio() {
        return edadPromedio;
    }

    public void setEdadPromedio(Double edadPromedio) {
        this.edadPromedio = edadPromedio;
    }
}