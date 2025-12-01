package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.RegistroEvento;
import upc.edu.pe.ecochips.Repositories.IRegistroEventoRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRegistroEventoService;

import java.util.List;

@Service
public class RegistroEventoServiceImplements implements IRegistroEventoService {

    @Autowired
    private IRegistroEventoRepository reR;

    @Override
    public List<RegistroEvento> list() {
        return reR.findAll();
    }

    @Override
    public void insert(RegistroEvento registroEvento) {
        reR.save(registroEvento);
    }

    @Override
    public RegistroEvento listId(int id) {
        return reR.findById(id).orElse(null);
    }

    @Override
    public void update(RegistroEvento registroEvento) {
        reR.save(registroEvento);
    }

    @Override
    public void delete(int id) {
        reR.deleteById(id);
    }

    @Override
    public long contarPersonasPorEvento(Integer idEvento) {
        return reR.countByEventoId(idEvento);
    }
}