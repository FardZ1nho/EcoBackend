package upc.edu.pe.ecochips.DTOs;

public class TipoRecomendacionAsignadoDTO {
    private String tipo;
    private Long vecesAsignado;

    public TipoRecomendacionAsignadoDTO(String tipo, Long vecesAsignado) {
        this.tipo = tipo;
        this.vecesAsignado = vecesAsignado;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getVecesAsignado() { return vecesAsignado; }
    public void setVecesAsignado(Long vecesAsignado) { this.vecesAsignado = vecesAsignado; }
}