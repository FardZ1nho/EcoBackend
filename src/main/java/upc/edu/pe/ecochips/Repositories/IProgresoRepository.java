package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Progreso;

@Repository
public interface IProgresoRepository extends JpaRepository<Progreso, Integer> {

    Progreso findByUsuarioIdUsuario(Integer idUsuario);
}