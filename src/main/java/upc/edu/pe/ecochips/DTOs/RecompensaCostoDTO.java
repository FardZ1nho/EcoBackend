package upc.edu.pe.ecochips.DTOs;

public class RecompensaCostoDTO {
    private String tituloRecompensa;
    private Integer costoCanjes;

    public RecompensaCostoDTO(String tituloRecompensa, Integer costoCanjes) {
        this.tituloRecompensa = tituloRecompensa;
        this.costoCanjes = costoCanjes;
    }

    // Getters y Setters
    public String getTituloRecompensa() { return tituloRecompensa; }
    public void setTituloRecompensa(String tituloRecompensa) { this.tituloRecompensa = tituloRecompensa; }

    public Integer getCostoCanjes() { return costoCanjes; }
    public void setCostoCanjes(Integer costoCanjes) { this.costoCanjes = costoCanjes; }
}