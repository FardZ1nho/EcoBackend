package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Recomendacion;

import java.util.List;

@Repository
public interface IRecomendacionRepository extends JpaRepository<Recomendacion, Integer> {
    @Query("SELECT r.tipo, COUNT(r.idRecomendacion) as cantidad " +
            "FROM Recomendacion r " +
            "GROUP BY r.tipo " +
            "ORDER BY cantidad DESC")
    List<Object[]> findRecomendacionesPorTipo();
}
