package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.RecomendacionTipoDTO;
import upc.edu.pe.ecochips.DTOs.TipoRecomendacionAsignadoDTO;
import upc.edu.pe.ecochips.Entities.Recomendacion;
import java.util.List;

public interface IRecomendacionService {
    List<Recomendacion> list();
    void insert(Recomendacion recomendacion);
    Recomendacion listId(int id);
    void update(Recomendacion recomendacion);
    void delete(int id);

    List<RecomendacionTipoDTO> findRecomendacionesPorTipo();

}