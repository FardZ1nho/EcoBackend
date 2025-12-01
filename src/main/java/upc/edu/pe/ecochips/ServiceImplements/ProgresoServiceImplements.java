package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.Progreso;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IProgresoRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IProgresoService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgresoServiceImplements implements IProgresoService {

    @Autowired
    private IProgresoRepository pR;

    @Autowired
    private IUserRepository uR;

    @Override
    public List<Progreso> list() {
        return pR.findAll();
    }

    @Override
    public void insert(Progreso progreso) {
        pR.save(progreso);
    }

    @Override
    public Progreso listId(int id) {  // Cambiado de Integer a int
        return pR.findById(id).orElse(null);
    }

    @Override
    public void update(Progreso progreso) {
        pR.save(progreso);
    }

    @Override
    public void delete(int id) {      // Cambiado de Integer a int
        pR.deleteById(id);
    }

    @Override
    public Progreso obtenerProgresoPorUsuario(Integer idUsuario) {
        Progreso progreso = pR.findByUsuarioIdUsuario(idUsuario);
        if (progreso == null) {
            // Buscar el usuario primero
            Usuario usuario = uR.findById(idUsuario).orElse(null);
            if (usuario != null) {
                progreso = new Progreso();
                progreso.setUsuario(usuario);
                progreso.setPuntos(0);
                progreso.setEstado("Activo");
                progreso.setFecha(LocalDate.now());
                pR.save(progreso);
            }
        }
        return progreso;
    }

    @Override
    public void agregarPuntos(Integer idUsuario, Integer puntos) {
        Progreso progreso = obtenerProgresoPorUsuario(idUsuario);
        int puntosAnteriores = progreso.getPuntos();
        progreso.setPuntos(puntosAnteriores + puntos);
        progreso.setFecha(LocalDate.now());

        // Verificar si sube de nivel (cada 10 puntos = 1 nivel)
        int nivelAnterior = (puntosAnteriores / 10) + 1;
        int nuevoNivel = (progreso.getPuntos() / 10) + 1;

        if (nuevoNivel > nivelAnterior) {
            // El usuario subió de nivel - dar 1 canje
            Usuario usuario = uR.findById(idUsuario).orElse(null);
            if (usuario != null) {
                usuario.setNivel(nuevoNivel);
                usuario.setCanjesDisponibles(usuario.getCanjesDisponibles() + 1);
                uR.save(usuario);
            }
        }

        pR.save(progreso);
    }

    @Override
    public void cambiarEstado(Integer idUsuario, String estado) {
        Progreso progreso = obtenerProgresoPorUsuario(idUsuario);
        progreso.setEstado(estado);
        progreso.setFecha(LocalDate.now());
        pR.save(progreso);
    }
}