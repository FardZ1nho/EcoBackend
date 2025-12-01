package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Transporte")
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransporte;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "factor_co2", nullable = false)
    private Double factorCo2;

    // Constructores
    public Transporte() {
    }

    public Transporte(int idTransporte, String nombre, Double factorCo2) {
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