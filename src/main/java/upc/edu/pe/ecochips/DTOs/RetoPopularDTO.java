package upc.edu.pe.ecochips.DTOs;

public class RetoPopularDTO {
    private String titulo;
    private Long cantidadParticipantes;

    public RetoPopularDTO(String titulo, Long cantidadParticipantes) {
        this.titulo = titulo;
        this.cantidadParticipantes = cantidadParticipantes;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Long getCantidadParticipantes() { return cantidadParticipantes; }
    public void setCantidadParticipantes(Long cantidadParticipantes) { this.cantidadParticipantes = cantidadParticipantes; }
}