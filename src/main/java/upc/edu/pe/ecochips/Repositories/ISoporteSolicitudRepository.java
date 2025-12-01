package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.SoporteSolicitud;

import java.util.List;

@Repository
public interface ISoporteSolicitudRepository  extends JpaRepository<SoporteSolicitud, Integer> {
    @Query("SELECT s FROM SoporteSolicitud s WHERE s.titulo = :titulo")
    List<SoporteSolicitud> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT s FROM SoporteSolicitud s WHERE s.Apartado = :Apartado")
    List<SoporteSolicitud> listarPorEstado(@Param("Apartado") String estado);

    @Query("SELECT s.Apartado, COUNT(s.idSoporteSolicitud) as cantidadSolicitudes " +
            "FROM SoporteSolicitud s " +
            "GROUP BY s.Apartado " +
            "ORDER BY cantidadSolicitudes DESC")
    List<Object[]> findSolicitudesPorApartado();
}
