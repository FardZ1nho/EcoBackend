package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.Rol;

import java.util.List;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {

    // Método para buscar rol por nombre exacto
    Rol findByNombre(String nombre);

    // Método para buscar roles que contengan un texto en el nombre
    List<Rol> findByNombreContaining(String nombre);

    // Método personalizado usando @Query (este es el que necesitas para buscarR)
    @Query("SELECT r FROM Rol r WHERE r.nombre LIKE %:nombre%")
    List<Rol> buscarR(@Param("nombre") String nombre);

    // Método alternativo si prefieres usar la nomenclatura de Spring Data JPA
    List<Rol> findByNombreContainingIgnoreCase(String nombre);
}