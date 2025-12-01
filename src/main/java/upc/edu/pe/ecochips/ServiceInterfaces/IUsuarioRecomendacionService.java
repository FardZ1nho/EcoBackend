package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.TipoRecomendacionAsignadoDTO;
import upc.edu.pe.ecochips.Entities.Recomendacion;
import upc.edu.pe.ecochips.Entities.UsuarioRecomendacion;
import java.util.List;

public interface IUsuarioRecomendacionService {
    List<UsuarioRecomendacion> list();
    void insert(UsuarioRecomendacion usuarioRecomendacion);
    UsuarioRecomendacion listId(int id);
    void update(UsuarioRecomendacion usuarioRecomendacion);
    void delete(int id);

    // Métodos específicos con tus queries
    List<Recomendacion> listarRecomendacionesPorUsuario(Integer idUsuario);
    List<Recomendacion> filtrarRecomendacionesPorTipo(String filtro);
    String asignarRecomendacion(Integer idUsuario, Integer idRecomendacion);


    // Nuevo método para el reporte
    List<TipoRecomendacionAsignadoDTO> findTiposRecomendacionMasAsignados();
}