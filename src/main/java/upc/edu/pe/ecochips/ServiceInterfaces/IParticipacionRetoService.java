package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.TopUsuarioDTO;
import upc.edu.pe.ecochips.Entities.ParticipacionReto;
import java.util.List;

public interface IParticipacionRetoService {
    List<ParticipacionReto> list();
    void insert(ParticipacionReto participacionReto);
    ParticipacionReto listId(int id);
    void update(ParticipacionReto participacionReto);
    void delete(int id);

    // Métodos específicos
    List<ParticipacionReto> listarPorUsuario(Integer idUsuario);
    long contarParticipantesPorReto(Integer idReto);
    String completarReto(Integer idUsuario, Integer idReto);

    // Nuevo método
    String eliminarParticipacion(Integer idUsuario, Integer idReto);

    // Nuevo método para el reporte
    List<TopUsuarioDTO> findTopUsuariosConMasRetosCompletados();
}