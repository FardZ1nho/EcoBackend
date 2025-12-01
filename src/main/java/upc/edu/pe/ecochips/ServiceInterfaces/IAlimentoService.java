package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.TipoAlimentoCO2DTO;
import upc.edu.pe.ecochips.Entities.Alimento;
import java.util.List;

public interface IAlimentoService {
    List<Alimento> list();
    void insert(Alimento alimento);
    Alimento listId(int id);
    void update(Alimento alimento);
    void delete(int id);

    // Nuevo método para el reporte
    List<TipoAlimentoCO2DTO> findPromedioCO2PorTipoAlimento();
}