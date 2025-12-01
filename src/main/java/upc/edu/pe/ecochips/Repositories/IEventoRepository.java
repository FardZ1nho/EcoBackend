package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Evento;

@Repository
public interface IEventoRepository extends JpaRepository<Evento,Integer> {
}
