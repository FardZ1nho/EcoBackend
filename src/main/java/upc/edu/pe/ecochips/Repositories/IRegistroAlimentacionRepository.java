package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.RegistroAlimentacion;
import upc.edu.pe.ecochips.Entities.Usuario;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IRegistroAlimentacionRepository extends JpaRepository<RegistroAlimentacion, Integer> {

    // Devuelve todos los registros del usuario (ya lo tenías)
    List<RegistroAlimentacion> findByUsuarioIdUsuario(Integer idUsuario);

    // Suma de porciones SOLO para un alimento específico en el periodo
    @Query("SELECT COALESCE(SUM(r.porciones), 0) FROM RegistroAlimentacion r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.alimento.idAlimento = :idAlimento " +
            "AND r.fecha BETWEEN :inicio AND :fin")
    Integer sumarPorcionesPorAlimentoEnPeriodo(@Param("idUsuario") Integer idUsuario,
                                               @Param("idAlimento") Integer idAlimento,
                                               @Param("inicio") LocalDate inicio,
                                               @Param("fin") LocalDate fin);

    // Suma de porciones TOTAL (cualquier alimento) en el periodo
    @Query("SELECT COALESCE(SUM(r.porciones), 0) FROM RegistroAlimentacion r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.fecha BETWEEN :inicio AND :fin")
    Integer sumarPorcionesTotalesEnPeriodo(@Param("idUsuario") Integer idUsuario,
                                           @Param("inicio") LocalDate inicio,
                                           @Param("fin") LocalDate fin);

    // Método previo que tienes para co2 total por usuario
    @Query("SELECT SUM(r.co2Emitido) FROM RegistroAlimentacion r WHERE r.usuario.idUsuario = :idUsuario")
    Double sumarCo2EmitidoPorUsuario(@Param("idUsuario") Integer idUsuario);

    List<RegistroAlimentacion> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin);

    // Suma de CO₂ emitido por alimentación en el periodo del reto
    @Query("SELECT COALESCE(SUM(r.co2Emitido), 0) FROM RegistroAlimentacion r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.fecha BETWEEN :inicio AND :fin")
    Double sumarCo2PorUsuarioEnPeriodo(@Param("idUsuario") Integer idUsuario,
                                       @Param("inicio") LocalDate inicio,
                                       @Param("fin") LocalDate fin);

}
