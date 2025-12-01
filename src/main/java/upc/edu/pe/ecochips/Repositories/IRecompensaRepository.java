package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Recompensa;

import java.util.List;

@Repository
public interface IRecompensaRepository extends JpaRepository<Recompensa, Integer> {

    // Buscar recompensas por costo de canjes
    @Query("SELECT r FROM Recompensa r WHERE r.costoCanjes = :costoCanjes")
    List<Recompensa> findByCostoCanjes(@Param("costoCanjes") int costoCanjes);

    // Buscar recompensas por título (búsqueda parcial)
    @Query("SELECT r FROM Recompensa r WHERE r.tituloRecompensa LIKE %:titulo%")
    List<Recompensa> findByTituloContaining(@Param("titulo") String titulo);

    // Recompensas más costosas (orden descendente)
    @Query("SELECT r.tituloRecompensa, r.costoCanjes " +
            "FROM Recompensa r " +
            "ORDER BY r.costoCanjes DESC")
    List<Object[]> findAllRecompensasMasCostosas();

    // Recompensas más baratas (orden ascendente)
    @Query("SELECT r.tituloRecompensa, r.costoCanjes " +
            "FROM Recompensa r " +
            "ORDER BY r.costoCanjes ASC")
    List<Object[]> findAllRecompensasMasBaratas();
}