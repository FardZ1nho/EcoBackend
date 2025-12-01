package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.SoporteRespuestaListDTO;
import upc.edu.pe.ecochips.Entities.SoporteRespuesta;
import upc.edu.pe.ecochips.Repositories.ISoporteRespuestaRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.ISoporteRespuestaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoporteRespuestaImplements implements ISoporteRespuestaService {
    @Autowired
    private ISoporteRespuestaRepository srR;

    @Override
    public List<SoporteRespuesta> listarRespuestas() {
        return srR.findAll();
    }

    @Override
    public SoporteRespuesta obtenerRespuestaPorId(int id) {
        return srR.findById(id).orElse(null);
    }

    @Override
    public SoporteRespuesta guardarRespuesta(SoporteRespuesta respuesta) {
        return srR.save(respuesta);
    }

    @Override
    public void delete(int id) {
        srR.deleteById(id);
    }

    @Override
    public void update(SoporteRespuesta SoporteRespuesta) {
        srR.save(SoporteRespuesta);
    }

    @Override
    public void insert(SoporteRespuesta SoporteRespuesta) {
        srR.save(SoporteRespuesta);
    }

    @Override
    public List<SoporteRespuestaListDTO> listarRespuestasReducidas() {
        return srR.findAll().stream().map(resp -> {
            SoporteRespuestaListDTO dto = new SoporteRespuestaListDTO();

            dto.setIdRespuesta(resp.getIdRespuesta());
            dto.setRespuesta(resp.getRespuesta());
            dto.setFechahora(resp.getFechahora());

            // ✅ INFORMACIÓN DEL USUARIO QUE RESPONDE
            if (resp.getUsuarioRespuesta() != null) {
                dto.setNombreUsuarioRespuesta(resp.getUsuarioRespuesta().getNombre());
                dto.setCorreoUsuarioRespuesta(resp.getUsuarioRespuesta().getCorreo());
            } else {
                dto.setNombreUsuarioRespuesta("Desconocido");
                dto.setCorreoUsuarioRespuesta("No disponible");
            }

            // ✅ INFORMACIÓN DE LA SOLICITUD Y QUIÉN PREGUNTA
            if (resp.getSolicitud() != null) {
                dto.setTituloSolicitud(resp.getSolicitud().getTitulo());
                dto.setDescripcionSolicitud(resp.getSolicitud().getDescripcion());
                dto.setEstadoSolicitud(resp.getSolicitud().getApartado());

                // ✅ INFORMACIÓN DEL USUARIO QUE PREGUNTA
                if (resp.getSolicitud().getUsuario() != null) {
                    dto.setNombreUsuarioSolicitud(resp.getSolicitud().getUsuario().getNombre());
                    dto.setCorreoUsuarioSolicitud(resp.getSolicitud().getUsuario().getCorreo());
                } else {
                    dto.setNombreUsuarioSolicitud("Desconocido");
                    dto.setCorreoUsuarioSolicitud("No disponible");
                }
            } else {
                dto.setTituloSolicitud("Sin solicitud asociada");
                dto.setDescripcionSolicitud("N/A");
                dto.setEstadoSolicitud("N/A");
                dto.setNombreUsuarioSolicitud("Desconocido");
                dto.setCorreoUsuarioSolicitud("No disponible");
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
