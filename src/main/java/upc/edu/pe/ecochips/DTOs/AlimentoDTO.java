package upc.edu.pe.ecochips.DTOs;

public class AlimentoDTO {
    private Integer idAlimento;
    private String nombre;
    private String tipo;
    private Double co2Porcion;

    // Constructores
    public AlimentoDTO() {
    }

    public AlimentoDTO(Integer idAlimento, String nombre, String tipo, Double co2Porcion) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
        this.tipo = tipo;
        this.co2Porcion = co2Porcion;
    }

    // Getters y Setters
    public Integer getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Integer idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCo2Porcion() {
        return co2Porcion;
    }

    public void setCo2Porcion(Double co2Porcion) {
        this.co2Porcion = co2Porcion;
    }
}