package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;

public class RegistroAlimentacionDTO {
    private Integer idRegistroAlimentacion;
    private Integer idUsuario;
    private Integer idAlimento;
    private Integer porciones;
    private Double co2Emitido;
    private LocalDate fecha;

    // Constructores
    public RegistroAlimentacionDTO() {
    }

    public RegistroAlimentacionDTO(Integer idRegistroAlimentacion, Integer idUsuario, Integer idAlimento,
                                   Integer porciones, Double co2Emitido, LocalDate fecha) {
        this.idRegistroAlimentacion = idRegistroAlimentacion;
        this.idUsuario = idUsuario;
        this.idAlimento = idAlimento;
        this.porciones = porciones;
        this.co2Emitido = co2Emitido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getIdRegistroAlimentacion() {
        return idRegistroAlimentacion;
    }

    public void setIdRegistroAlimentacion(Integer idRegistroAlimentacion) {
        this.idRegistroAlimentacion = idRegistroAlimentacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Integer idAlimento) {
        this.idAlimento = idAlimento;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) {
        this.porciones = porciones;
    }

    public Double getCo2Emitido() {
        return co2Emitido;
    }

    public void setCo2Emitido(Double co2Emitido) {
        this.co2Emitido = co2Emitido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}