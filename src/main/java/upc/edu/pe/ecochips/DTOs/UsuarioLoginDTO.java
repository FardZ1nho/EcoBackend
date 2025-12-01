package upc.edu.pe.ecochips.DTOs;

public class UsuarioLoginDTO {
    private String correo;
    private String contrasena;

    // Constructores
    public UsuarioLoginDTO() {}

    public UsuarioLoginDTO(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}