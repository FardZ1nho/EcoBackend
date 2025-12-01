package upc.edu.pe.ecochips.DTOs;

public class TipoAlimentoCO2DTO {
    private String tipo;
    private Double promedioCO2;
    private Long cantidadAlimentos;

    public TipoAlimentoCO2DTO(String tipo, Double promedioCO2, Long cantidadAlimentos) {
        this.tipo = tipo;
        this.promedioCO2 = promedioCO2;
        this.cantidadAlimentos = cantidadAlimentos;
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getPromedioCO2() { return promedioCO2; }
    public void setPromedioCO2(Double promedioCO2) { this.promedioCO2 = promedioCO2; }

    public Long getCantidadAlimentos() { return cantidadAlimentos; }
    public void setCantidadAlimentos(Long cantidadAlimentos) { this.cantidadAlimentos = cantidadAlimentos; }
}