package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.DTOs.RetoPopularDTO;
import upc.edu.pe.ecochips.Entities.Reto;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IRetoRepository extends JpaRepository<Reto, Integer> {
    @Query("SELECT r FROM Reto r WHERE :fecha BETWEEN r.fechaInicio AND r.fechaFin AND r.idReto NOT IN " +
            "(SELECT pr.reto.idReto FROM ParticipacionReto pr WHERE pr.usuario.idUsuario = :idUsuario)")
    List<Reto> findRetosDisponiblesParaUsuario(
            @Param("idUsuario") Integer idUsuario,
            @Param("fecha") LocalDate fecha);


    @Query("SELECT r.titulo, COUNT(pr.idParticipacion) as cantidadParticipantes " +
            "FROM Reto r JOIN r.participaciones pr " +
            "WHERE pr.completado = true " +
            "GROUP BY r.idReto, r.titulo " +
            "ORDER BY cantidadParticipantes DESC")
    List<Object[]> findRetosMasPopulares();

}
