package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.Entities.RegistroEvento;
import java.util.List;

public interface IRegistroEventoService {
    List<RegistroEvento> list();
    void insert(RegistroEvento registroEvento);
    RegistroEvento listId(int id);
    void update(RegistroEvento registroEvento);
    void delete(int id);

    // Método específico
    long contarPersonasPorEvento(Integer idEvento);
}