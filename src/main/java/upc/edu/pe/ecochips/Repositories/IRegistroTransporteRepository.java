package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.RegistroTransporte;
import upc.edu.pe.ecochips.Entities.Usuario;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IRegistroTransporteRepository extends JpaRepository<RegistroTransporte, Integer> {

    // Devuelve registros del usuario (ya lo tenías)
    List<RegistroTransporte> findByUsuarioIdUsuario(Integer idUsuario);

    // Suma de distancia SOLO para un transporte específico en el periodo
    @Query("SELECT COALESCE(SUM(r.distanciaKm), 0) FROM RegistroTransporte r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.transporte.idTransporte = :idTransporte " +
            "AND r.fecha BETWEEN :inicio AND :fin")
    Double sumarDistanciaPorTransporteEnPeriodo(@Param("idUsuario") Integer idUsuario,
                                                @Param("idTransporte") Integer idTransporte,
                                                @Param("inicio") LocalDate inicio,
                                                @Param("fin") LocalDate fin);

    // Suma de distancia TOTAL (cualquier transporte) en el periodo
    @Query("SELECT COALESCE(SUM(r.distanciaKm), 0) FROM RegistroTransporte r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.fecha BETWEEN :inicio AND :fin")
    Double sumarDistanciaTotalEnPeriodo(@Param("idUsuario") Integer idUsuario,
                                        @Param("inicio") LocalDate inicio,
                                        @Param("fin") LocalDate fin);

    // Método previo que tienes para co2 total por usuario
    @Query("SELECT SUM(r.co2Emitido) FROM RegistroTransporte r WHERE r.usuario.idUsuario = :idUsuario")
    Double sumarCo2EmitidoPorUsuario(@Param("idUsuario") Integer idUsuario);

    List<RegistroTransporte> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin);

    // Suma de CO₂ emitido por transporte en el periodo del reto
    @Query("SELECT COALESCE(SUM(r.co2Emitido), 0) FROM RegistroTransporte r " +
            "WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.fecha BETWEEN :inicio AND :fin")
    Double sumarCo2PorUsuarioEnPeriodo(@Param("idUsuario") Integer idUsuario,
                                       @Param("inicio") LocalDate inicio,
                                       @Param("fin") LocalDate fin);

}
