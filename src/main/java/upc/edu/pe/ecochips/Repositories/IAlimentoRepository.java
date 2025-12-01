package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Alimento;

import java.util.List;

@Repository
public interface IAlimentoRepository extends JpaRepository<Alimento, Integer> {
    @Query("SELECT a.tipo, AVG(a.co2Porcion) as promedioCO2, COUNT(a.idAlimento) as cantidadAlimentos " +
            "FROM Alimento a " +
            "GROUP BY a.tipo " +
            "ORDER BY promedioCO2 DESC")
    List<Object[]> findPromedioCO2PorTipoAlimento();
}