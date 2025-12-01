package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.Entities.RegistroAlimentacion;
import java.util.List;

public interface IRegistroAlimentacionService {
    List<RegistroAlimentacion> list();
    void insert(RegistroAlimentacion registroAlimentacion);
    RegistroAlimentacion listId(int id);
    void update(RegistroAlimentacion registroAlimentacion);
    void delete(int id);
    List<RegistroAlimentacion> listarPorUsuario(Integer idUsuario);
    // ELIMINADO: void actualizarCo2TotalUsuario(Integer idUsuario);
}