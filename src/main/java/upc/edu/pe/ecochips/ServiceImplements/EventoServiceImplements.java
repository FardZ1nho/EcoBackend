package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.Evento;
import upc.edu.pe.ecochips.Repositories.IEventoRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IEventoService;

import java.util.List;

@Service
public class EventoServiceImplements implements IEventoService {
    @Autowired
    private IEventoRepository eR;

    @Override
    public List<Evento> list() {
        return eR.findAll();
    }

    @Override
    public Evento listId(int id) {
        return eR.findById(id).orElse(null);
    }

    @Override
    public void insert(Evento evento) {
        eR.save(evento);
    }

    @Override
    public void delete(int id) {
        eR.deleteById(id);
    }

    @Override
    public void update(Evento evento) {
        eR.save(evento);
    }



}
