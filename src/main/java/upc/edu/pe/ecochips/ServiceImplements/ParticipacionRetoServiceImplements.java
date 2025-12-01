package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upc.edu.pe.ecochips.DTOs.TopUsuarioDTO;
import upc.edu.pe.ecochips.Entities.ParticipacionReto;
import upc.edu.pe.ecochips.Entities.Reto;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IParticipacionRetoRepository;
import upc.edu.pe.ecochips.Repositories.IRetoRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IParticipacionRetoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipacionRetoServiceImplements implements IParticipacionRetoService {

    @Autowired
    private IParticipacionRetoRepository prR;

    @Autowired
    private IUserRepository uR;

    @Autowired
    private IRetoRepository rR;

    @Override
    public List<ParticipacionReto> list() {
        return prR.findAll();
    }

    @Override
    public void insert(ParticipacionReto participacionReto) {
        prR.save(participacionReto);
    }

    @Override
    public ParticipacionReto listId(int id) {
        return prR.findById(id).orElse(null);
    }

    @Override
    public void update(ParticipacionReto participacionReto) {
        prR.save(participacionReto);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional<ParticipacionReto> participacionOpt = prR.findById(id);
        if (participacionOpt.isPresent()) {
            ParticipacionReto participacion = participacionOpt.get();

            // Quitar canje si estaba completado
            if (participacion.isCompletado()) {
                Usuario usuario = participacion.getUsuario();
                int canjesActuales = usuario.getCanjesDisponibles();
                if (canjesActuales > 0) {
                    usuario.setCanjesDisponibles(canjesActuales - 1);
                    uR.save(usuario);
                }
            }

            prR.deleteById(id);
        }
    }

    @Override
    public List<ParticipacionReto> listarPorUsuario(Integer idUsuario) {
        return prR.findByUsuarioId(idUsuario);
    }

    @Override
    public long contarParticipantesPorReto(Integer idReto) {
        return prR.countByRetoId(idReto);
    }

    @Override
    @Transactional
    public String completarReto(Integer idUsuario, Integer idReto) {
        Usuario usuario = uR.findById(idUsuario).orElse(null);
        Reto reto = rR.findById(idReto).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado";
        }
        if (reto == null) {
            return "Reto no encontrado";
        }

        // Verificar si ya participó en el reto usando el nuevo método del repository
        Optional<ParticipacionReto> participacionExistente = prR.findByUsuarioIdAndRetoId(idUsuario, idReto);

        if (participacionExistente.isPresent()) {
            return "El usuario ya participó en este reto";
        }

        // Crear nueva participación
        ParticipacionReto participacion = new ParticipacionReto();
        participacion.setUsuario(usuario);
        participacion.setReto(reto);
        participacion.setCompletado(true);
        prR.save(participacion);

        // Dar 1 canje por completar reto
        usuario.setCanjesDisponibles(usuario.getCanjesDisponibles() + 1);
        uR.save(usuario);

        return "Reto completado exitosamente. Se ha agregado 1 canje disponible.";
    }

    @Override
    @Transactional
    public String eliminarParticipacion(Integer idUsuario, Integer idReto) {
        Optional<ParticipacionReto> participacionOpt = prR.findByUsuarioIdAndRetoId(idUsuario, idReto);

        if (participacionOpt.isPresent()) {
            ParticipacionReto participacion = participacionOpt.get();

            // Quitar canje si estaba completado
            if (participacion.isCompletado()) {
                Usuario usuario = participacion.getUsuario();
                int canjesActuales = usuario.getCanjesDisponibles();
                if (canjesActuales > 0) {
                    usuario.setCanjesDisponibles(canjesActuales - 1);
                    uR.save(usuario);
                }
            }

            prR.delete(participacion);
            return "Participación eliminada y canje removido exitosamente";
        }

        return "No se encontró la participación para este usuario y reto";
    }
    @Override
    public List<TopUsuarioDTO> findTopUsuariosConMasRetosCompletados() {
        List<Object[]> results = prR.findTopUsuariosConMasRetosCompletados();
        return results.stream()
                .map(result -> new TopUsuarioDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}