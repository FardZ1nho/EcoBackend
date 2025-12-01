package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.SoporteRespuesta;

@Repository
public interface ISoporteRespuestaRepository extends JpaRepository<SoporteRespuesta, Integer> {
}