package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.SoporteRespuestaDTO;
import upc.edu.pe.ecochips.DTOs.SoporteRespuestaListDTO;
import upc.edu.pe.ecochips.Entities.SoporteRespuesta;
import upc.edu.pe.ecochips.Entities.SoporteSolicitud;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.ISoporteSolicitudRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.ISoporteRespuestaService;

import java.util.List;

@RestController
// ✅ Asegúrate que esta ruta coincida con tu Angular
@RequestMapping("/soporterespuestas")
public class SoporteRespuestaController {

    @Autowired
    private ISoporteRespuestaService srS;

    @Autowired
    private ISoporteSolicitudRepository solicitudRepository;

    @Autowired
    private IUserRepository usuarioRepository;

    // ✅ MODIFICADO: Ahora USER también puede listar (para ver si le respondieron)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') or hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<SoporteRespuestaListDTO>> listar() {
        List<SoporteRespuestaListDTO> lista = srS.listarRespuestasReducidas();
        return ResponseEntity.ok(lista);
    }

    // ✅ SOLO ADMIN puede crear respuestas
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<String> insertar(@RequestBody SoporteRespuestaDTO dto) {
        try {
            ModelMapper m = new ModelMapper();
            SoporteRespuesta r = m.map(dto, SoporteRespuesta.class);

            // ✅ ASIGNAR SOLICITUD
            if (dto.getIdSolicitud() != 0) {
                SoporteSolicitud solicitud = solicitudRepository.findById(dto.getIdSolicitud()).orElse(null);
                if (solicitud != null) {
                    r.setSolicitud(solicitud);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("No existe una solicitud con el ID: " + dto.getIdSolicitud());
                }
            }

            // ✅ ASIGNAR USUARIO QUE RESPONDE
            if (dto.getIdUsuarioRespuesta() != 0) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuarioRespuesta()).orElse(null);
                if (usuario != null) {
                    r.setUsuarioRespuesta(usuario);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("No existe un usuario con el ID: " + dto.getIdUsuarioRespuesta());
                }
            }

            srS.insert(r);
            return ResponseEntity.ok("Respuesta registrada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar respuesta: " + e.getMessage());
        }
    }

    // ✅ SOLO ADMIN puede ver respuesta específica por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        SoporteRespuesta r = srS.obtenerRespuestaPorId(id);
        if (r == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una respuesta con el ID " + id);
        }
        SoporteRespuestaDTO dto = convertirADTO(r);
        return ResponseEntity.ok(dto);
    }

    // ✅ SOLO ADMIN puede eliminar respuestas
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        SoporteRespuesta r = srS.obtenerRespuestaPorId(id);
        if (r == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID " + id);
        }
        srS.delete(id);
        return ResponseEntity.ok("Registro con Id " + id + " eliminado correctamente");
    }

    // ✅ SOLO ADMIN puede modificar respuestas
    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<String> modificar(@RequestBody SoporteRespuestaDTO dto) {
        SoporteRespuesta existente = srS.obtenerRespuestaPorId(dto.getIdRespuesta());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID " + dto.getIdRespuesta());
        }

        try {
            ModelMapper m = new ModelMapper();
            SoporteRespuesta r = m.map(dto, SoporteRespuesta.class);

            // ✅ MANTENER O ASIGNAR SOLICITUD
            if (dto.getIdSolicitud() != 0) {
                SoporteSolicitud solicitud = solicitudRepository.findById(dto.getIdSolicitud()).orElse(null);
                if (solicitud != null) {
                    r.setSolicitud(solicitud);
                }
            } else {
                r.setSolicitud(existente.getSolicitud());
            }

            // ✅ MANTENER O ASIGNAR USUARIO QUE RESPONDE
            if (dto.getIdUsuarioRespuesta() != 0) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuarioRespuesta()).orElse(null);
                if (usuario != null) {
                    r.setUsuarioRespuesta(usuario);
                }
            } else {
                r.setUsuarioRespuesta(existente.getUsuarioRespuesta());
            }

            srS.update(r);
            return ResponseEntity.ok("Registro con Id " + dto.getIdRespuesta() + " modificado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar respuesta: " + e.getMessage());
        }
    }

    private SoporteRespuestaDTO convertirADTO(SoporteRespuesta respuesta) {
        SoporteRespuestaDTO dto = new SoporteRespuestaDTO();
        dto.setIdRespuesta(respuesta.getIdRespuesta());
        dto.setRespuesta(respuesta.getRespuesta());
        dto.setFechahora(respuesta.getFechahora());

        if (respuesta.getSolicitud() != null) {
            dto.setIdSolicitud(respuesta.getSolicitud().getIdSoporteSolicitud());
        }

        if (respuesta.getUsuarioRespuesta() != null) {
            dto.setIdUsuarioRespuesta(respuesta.getUsuarioRespuesta().getIdUsuario());
        }

        return dto;
    }
}