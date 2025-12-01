package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import upc.edu.pe.ecochips.Entities.Usuario;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<Usuario, Integer> {

    //Para LOGIN: Buscar por nombre
    Usuario findOneByNombre(String nombre);

    //Para REPORTES: Buscar por correo
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Usuario findByCorreo(@Param("correo") String correo);

    //CORRECTO - Buscar por nivel
    @Query("SELECT u FROM Usuario u WHERE u.nivel = :nivel")
    List<Usuario> findByNivel(@Param("nivel") int nivel);

    //CORRECTO - Distribución de participantes por género
    @Query(value = "SELECT u.genero, COUNT(DISTINCT u.id_usuario) AS cantidad_participantes, CAST(AVG(u.edad) AS DECIMAL(10, 2)) AS edad_promedio " +
            "FROM usuario u " +
            "INNER JOIN registro_evento ue ON u.id_usuario = ue.id_usuario " +
            "GROUP BY u.genero " +
            "ORDER BY cantidad_participantes DESC",
            nativeQuery = true)
    public List<Object[]> obtenerDistribucionParticipantesPorGenero();

    //CORRECTO - Usuarios con menor impacto ambiental
    @Query("SELECT u.nombre, u.co2Total, u.nivel " +
            "FROM Usuario u " +
            "WHERE u.co2Total IS NOT NULL " +
            "ORDER BY u.co2Total ASC")
    List<Object[]> findUsuariosConMenorImpactoAmbiental();

    //verificar si un correo existe
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo")
    int countByCorreo(@Param("correo") String correo);

    // En IUserRepository - agregar si necesitas verificar nombre único
    boolean existsByNombre(String nombre);
}