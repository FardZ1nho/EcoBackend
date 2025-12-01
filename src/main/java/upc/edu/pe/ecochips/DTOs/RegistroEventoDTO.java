package upc.edu.pe.ecochips.DTOs;

public class RegistroEventoDTO {
    private int idRegistroEvento;
    private int idUsuario;
    private int idEvento;
    // ❌ ELIMINADO: private LocalDate fechaRegistro;

    // Constructores
    public RegistroEventoDTO() {
    }

    public RegistroEventoDTO(int idRegistroEvento, int idUsuario, int idEvento) {
        this.idRegistroEvento = idRegistroEvento;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
    }

    // Getters y Setters
    public int getIdRegistroEvento() {
        return idRegistroEvento;
    }

    public void setIdRegistroEvento(int idRegistroEvento) {
        this.idRegistroEvento = idRegistroEvento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    // ❌ ELIMINADO: getFechaRegistro() y setFechaRegistro()
}