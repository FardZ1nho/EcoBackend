package upc.edu.pe.ecochips.DTOs;

import java.util.List;

public class UserDTO {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private int edad;
    private String genero;
    private Boolean enabled;
    private int nivel = 1;
    private Double co2Total = 0.0;
    private int canjesDisponibles = 0;
    private List<String> roles;

    // Constructores
    public UserDTO() {
    }

    public UserDTO(int idUsuario, String nombre, String correo, String contrasena,
                   int edad, String genero, Boolean enabled, int nivel,
                   Double co2Total, int canjesDisponibles, List<String> roles) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.edad = edad;
        this.genero = genero;
        this.enabled = enabled;
        this.nivel = nivel;
        this.co2Total = co2Total;
        this.canjesDisponibles = canjesDisponibles;
        this.roles = roles;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Double getCo2Total() {
        return co2Total;
    }

    public void setCo2Total(Double co2Total) {
        this.co2Total = co2Total;
    }

    public int getCanjesDisponibles() {
        return canjesDisponibles;
    }

    public void setCanjesDisponibles(int canjesDisponibles) {
        this.canjesDisponibles = canjesDisponibles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}