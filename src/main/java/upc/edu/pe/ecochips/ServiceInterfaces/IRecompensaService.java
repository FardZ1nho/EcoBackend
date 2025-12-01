package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.RecompensaCostoDTO;
import upc.edu.pe.ecochips.Entities.Recompensa;
import java.util.List;

public interface IRecompensaService {
    List<Recompensa> list();
    void insert(Recompensa recompensa);
    Recompensa listId(int id);
    void update(Recompensa recompensa);
    void delete(int id);

    // Métodos específicos
    List<Recompensa> buscarPorCostoCanjes(int costoCanjes);
    List<Recompensa> buscarPorTitulo(String titulo);

    // Nuevos métodos para los reportes
    List<RecompensaCostoDTO> findAllRecompensasMasCostosas();
    List<RecompensaCostoDTO> findAllRecompensasMasBaratas();
}