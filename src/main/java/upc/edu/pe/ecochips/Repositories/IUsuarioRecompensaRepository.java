package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.UsuarioRecompensa;

import java.util.List;

@Repository
public interface IUsuarioRecompensaRepository extends JpaRepository<UsuarioRecompensa, Integer> {
    // Buscar recompensas por usuario
    @Query("SELECT ur FROM UsuarioRecompensa ur WHERE ur.usuario.idUsuario = :idUsuario")
    List<UsuarioRecompensa> findByUsuarioId(@Param("idUsuario") Integer idUsuario);


    @Query("SELECT r.tituloRecompensa, COUNT(ur.idUsuarioRecompensa) as vecesCanjeada " +
            "FROM UsuarioRecompensa ur JOIN ur.recompensa r " +
            "GROUP BY r.idRecompensa, r.tituloRecompensa " +
            "ORDER BY vecesCanjeada DESC")
    List<Object[]> findRecompensasMasPopulares();
}
