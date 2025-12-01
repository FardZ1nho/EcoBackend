package upc.edu.pe.ecochips.DTOs;

public class TopUsuarioDTO {
    private String nombreUsuario;
    private Long retosCompletados;

    public TopUsuarioDTO(String nombreUsuario, Long retosCompletados) {
        this.nombreUsuario = nombreUsuario;
        this.retosCompletados = retosCompletados;
    }

    // Getters y Setters
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public Long getRetosCompletados() { return retosCompletados; }
    public void setRetosCompletados(Long retosCompletados) { this.retosCompletados = retosCompletados; }
}