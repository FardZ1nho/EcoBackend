package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Transporte;

import java.util.List;

@Repository
public interface ITransporteRepository extends JpaRepository<Transporte,Integer> {

    @Query("SELECT t.nombre, t.factorCo2 " +
            "FROM Transporte t " +
            "ORDER BY t.factorCo2 DESC " +
            "LIMIT 5")
    List<Object[]> findTop5TransportesMasContaminantes();
}
