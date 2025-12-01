package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.ParticipacionReto;

import java.util.List;
import java.util.Optional;

@Repository
public interface IParticipacionRetoRepository extends JpaRepository<ParticipacionReto, Integer> {

    // Buscar participaciones por usuario
    @Query("SELECT pr FROM ParticipacionReto pr WHERE pr.usuario.idUsuario = :idUsuario")
    List<ParticipacionReto> findByUsuarioId(@Param("idUsuario") Integer idUsuario);

    // Buscar participación específica por usuario y reto
    @Query("SELECT pr FROM ParticipacionReto pr WHERE pr.usuario.idUsuario = :idUsuario AND pr.reto.idReto = :idReto")
    Optional<ParticipacionReto> findByUsuarioIdAndRetoId(@Param("idUsuario") Integer idUsuario, @Param("idReto") Integer idReto);

    // Contar participantes por reto
    @Query("SELECT COUNT(pr) FROM ParticipacionReto pr WHERE pr.reto.idReto = :idReto")
    long countByRetoId(@Param("idReto") Integer idReto);

    // En IParticipacionRetoRepository - añade este método:
    @Query("SELECT u.nombre, COUNT(pr.idParticipacion) as retosCompletados " +
            "FROM ParticipacionReto pr JOIN pr.usuario u " +
            "WHERE pr.completado = true " +
            "GROUP BY u.idUsuario, u.nombre " +
            "ORDER BY retosCompletados DESC")
    List<Object[]> findTopUsuariosConMasRetosCompletados();
}