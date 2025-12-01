package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.TipoRecomendacionAsignadoDTO;
import upc.edu.pe.ecochips.Entities.Recomendacion;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Entities.UsuarioRecomendacion;
import upc.edu.pe.ecochips.Repositories.IRecomendacionRepository;
import upc.edu.pe.ecochips.Repositories.IUsuarioRecomendacionRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IUsuarioRecomendacionService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioRecomendacionServiceImplements implements IUsuarioRecomendacionService {

    @Autowired
    private IUsuarioRecomendacionRepository urR;

    @Autowired
    private IUserRepository uR;

    @Autowired
    private IRecomendacionRepository rR;

    @Override
    public List<UsuarioRecomendacion> list() {
        return urR.findAll();
    }

    @Override
    public void insert(UsuarioRecomendacion usuarioRecomendacion) {
        urR.save(usuarioRecomendacion);
    }

    @Override
    public UsuarioRecomendacion listId(int id) {
        return urR.findById(id).orElse(null);
    }

    @Override
    public void update(UsuarioRecomendacion usuarioRecomendacion) {
        urR.save(usuarioRecomendacion);
    }

    @Override
    public void delete(int id) {
        urR.deleteById(id);
    }

    @Override
    public List<Recomendacion> listarRecomendacionesPorUsuario(Integer idUsuario) {
        return urR.findRecomendacionesPorUsuario(idUsuario);
    }

    @Override
    public List<Recomendacion> filtrarRecomendacionesPorTipo(String filtro) {
        return urR.filtrarPorTipo(filtro);
    }

    @Override
    public String asignarRecomendacion(Integer idUsuario, Integer idRecomendacion) {
        Usuario usuario = uR.findById(idUsuario).orElse(null);
        Recomendacion recomendacion = rR.findById(idRecomendacion).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado";
        }
        if (recomendacion == null) {
            return "Recomendación no encontrada";
        }

        // Asignar recomendación al usuario
        UsuarioRecomendacion ur = new UsuarioRecomendacion();
        ur.setUsuario(usuario);
        ur.setRecomendacion(recomendacion);
        ur.setFechaAsignacion(LocalDate.now());
        urR.save(ur);

        return "Recomendación asignada correctamente al usuario: " + recomendacion.getTitulo();
    }
    // En UsuarioRecomendacionServiceImplement.java
    @Override
    public List<TipoRecomendacionAsignadoDTO> findTiposRecomendacionMasAsignados() {
        List<Object[]> results = urR.findTiposRecomendacionMasAsignados();
        return results.stream()
                .map(result -> new TipoRecomendacionAsignadoDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}