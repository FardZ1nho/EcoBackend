package upc.edu.pe.ecochips.Entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name="nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;

    @Column(name="contrasena", length = 100, nullable = false)
    private String contrasena;

    @Column(name="edad", length = 3, nullable = false)
    private int edad;

    @Column(name="genero", length = 50, nullable = false)
    private String genero;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "nivel", nullable = false)
    private int nivel = 1;

    // CORREGIDO: Quitar precision y scale para Double
    @Column(name = "co2_total")
    private Double co2Total = 0.0;

    @Column(name = "canjes_disponibles", nullable = false)
    private int canjesDisponibles = 0;

    // RELACIÓN MUCHOS-A-MUCHOS
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private List<Rol> roles = new ArrayList<>();

    // Constructores
    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String correo, String contrasena,
                   int edad, String genero, Boolean enabled, int nivel,
                   Double co2Total, int canjesDisponibles) {
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
        this.roles = new ArrayList<>();
    }

    // Getters y Setters (mantener igual)
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public Double getCo2Total() { return co2Total; }
    public void setCo2Total(Double co2Total) { this.co2Total = co2Total; }
    public int getCanjesDisponibles() { return canjesDisponibles; }
    public void setCanjesDisponibles(int canjesDisponibles) { this.canjesDisponibles = canjesDisponibles; }
    public List<Rol> getRoles() { return roles; }
    public void setRoles(List<Rol> roles) { this.roles = roles; }

    // Métodos para manejar roles
    public void addRol(Rol rol) {
        if (!this.roles.contains(rol)) {
            this.roles.add(rol);
        }
    }

    public void removeRol(Rol rol) {
        this.roles.remove(rol);
    }

    public boolean hasRol(String nombreRol) {
        return this.roles.stream().anyMatch(rol -> rol.getNombre().equals(nombreRol));
    }
}