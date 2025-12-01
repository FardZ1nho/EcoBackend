package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    public Rol() {
    }

    public Rol(int idRol, String nombre, String descripcion) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}