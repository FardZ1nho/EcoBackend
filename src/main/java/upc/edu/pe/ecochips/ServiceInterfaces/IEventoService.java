package upc.edu.pe.ecochips.ServiceInterfaces;


import upc.edu.pe.ecochips.Entities.Evento;

import java.util.List;

public interface IEventoService {
    public List<Evento> list();
    public Evento listId(int id);
    public void insert(Evento evento);
    void delete(int id);
    void update(Evento evento);
}
