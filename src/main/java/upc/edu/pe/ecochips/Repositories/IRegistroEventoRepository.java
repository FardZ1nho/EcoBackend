package upc.edu.pe.ecochips.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.ecochips.Entities.RegistroEvento;

@Repository
public interface IRegistroEventoRepository extends JpaRepository<RegistroEvento, Integer> {

    // Contar cuántas personas se registraron a un evento específico
    @Query("SELECT COUNT(re) FROM RegistroEvento re WHERE re.evento.idEvento = :idEvento")
    long countByEventoId(@Param("idEvento") Integer idEvento);
}