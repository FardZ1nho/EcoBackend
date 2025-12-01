package upc.edu.pe.ecochips.DTOs;

public class RecompensaPopularDTO {
    private String tituloRecompensa;
    private Long vecesCanjeada;

    public RecompensaPopularDTO(String tituloRecompensa, Long vecesCanjeada) {
        this.tituloRecompensa = tituloRecompensa;
        this.vecesCanjeada = vecesCanjeada;
    }

    // Getters y Setters
    public String getTituloRecompensa() { return tituloRecompensa; }
    public void setTituloRecompensa(String tituloRecompensa) { this.tituloRecompensa = tituloRecompensa; }

    public Long getVecesCanjeada() { return vecesCanjeada; }
    public void setVecesCanjeada(Long vecesCanjeada) { this.vecesCanjeada = vecesCanjeada; }
}