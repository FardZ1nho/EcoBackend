package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "alimento")
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlimento;

    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @Column(name = "tipo", length = 100, nullable = false)
    private String tipo;

    @Column(name = "co2_porcion", nullable = false)
    private Double co2Porcion;

    // Constructores
    public Alimento() {
    }

    public Alimento(Integer idAlimento, String nombre, String tipo, Double co2Porcion) {
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