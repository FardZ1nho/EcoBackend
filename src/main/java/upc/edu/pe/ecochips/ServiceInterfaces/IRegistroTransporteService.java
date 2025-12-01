package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.Entities.RegistroTransporte;
import java.util.List;

public interface IRegistroTransporteService {
    List<RegistroTransporte> list();
    void insert(RegistroTransporte registroTransporte);
    RegistroTransporte listId(int id);
    void update(RegistroTransporte registroTransporte);
    void delete(int id);
    List<RegistroTransporte> listarPorUsuario(Integer idUsuario);
    // ELIMINADO: void actualizarCo2TotalUsuario(Integer idUsuario);
}