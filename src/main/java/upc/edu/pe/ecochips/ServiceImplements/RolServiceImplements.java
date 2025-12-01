package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.Rol;
import upc.edu.pe.ecochips.Repositories.IRolRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRolService;

import java.util.List;

@Service
public class RolServiceImplements implements IRolService {
    @Autowired
    private IRolRepository rR;

    @Override
    public List<Rol> list() { return rR.findAll();}

    @Override
    public void insert(Rol rol) { rR.save(rol);}

    @Override
    public Rol listId(int id) { return rR.findById(id).orElse(null);}

    @Override
    public void delete(int id) { rR.deleteById(id);}

    @Override
    public void update(Rol rol) { rR.save(rol);}

    @Override
    public List<Rol> buscarR(String nombre) { return rR.buscarR(nombre);}


}
