// SoporteApartadoDTO.java
package upc.edu.pe.ecochips.DTOs;

public class SoporteApartadoDTO {
    private String apartado;
    private Long cantidadSolicitudes;

    public SoporteApartadoDTO(String apartado, Long cantidadSolicitudes) {
        this.apartado = apartado;
        this.cantidadSolicitudes = cantidadSolicitudes;
    }

    // Getters y Setters
    public String getApartado() { return apartado; }
    public void setApartado(String apartado) { this.apartado = apartado; }

    public Long getCantidadSolicitudes() { return cantidadSolicitudes; }
    public void setCantidadSolicitudes(Long cantidadSolicitudes) { this.cantidadSolicitudes = cantidadSolicitudes; }
}