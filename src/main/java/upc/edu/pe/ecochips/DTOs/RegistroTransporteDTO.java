// RegistroTransporteDTO.java
package upc.edu.pe.ecochips.DTOs;

import java.time.LocalDate;

public class RegistroTransporteDTO {
    private Integer idRegistroTransporte;
    private Integer idUsuario;
    private Integer idTransporte;
    private Double distanciaKm;
    private Double co2Emitido;
    private LocalDate fecha;

    // Constructores
    public RegistroTransporteDTO() {
    }

    public RegistroTransporteDTO(Integer idRegistroTransporte, Integer idUsuario, Integer idTransporte,
                                 Double distanciaKm, Double co2Emitido, LocalDate fecha) {
        this.idRegistroTransporte = idRegistroTransporte;
        this.idUsuario = idUsuario;
        this.idTransporte = idTransporte;
        this.distanciaKm = distanciaKm;
        this.co2Emitido = co2Emitido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Integer getIdRegistroTransporte() {
        return idRegistroTransporte;
    }

    public void setIdRegistroTransporte(Integer idRegistroTransporte) {
        this.idRegistroTransporte = idRegistroTransporte;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(Integer idTransporte) {
        this.idTransporte = idTransporte;
    }

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
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