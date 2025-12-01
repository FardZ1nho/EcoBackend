package upc.edu.pe.ecochips.DTOs;

public class RecompensaDTO {
    private int idRecompensa;
    private String tituloRecompensa;
    private String descripcionRecompensa;
    private int costoCanjes;

    public int getIdRecompensa() {return idRecompensa;}

    public void setIdRecompensa(int idRecompensa) {this.idRecompensa = idRecompensa;}

    public String getTituloRecompensa() {return tituloRecompensa;}

    public void setTituloRecompensa(String tituloRecompensa) {this.tituloRecompensa = tituloRecompensa;}

    public String getDescripcionRecompensa() {return descripcionRecompensa;}

    public void setDescripcionRecompensa(String descripcionRecompensa) {this.descripcionRecompensa = descripcionRecompensa;}

    public int getCostoCanjes() {return costoCanjes;}

    public void setCostoCanjes(int costoCanjes) {this.costoCanjes = costoCanjes;}
}

