package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.SoporteApartadoDTO;
import upc.edu.pe.ecochips.DTOs.SoporteSolicitudDTO;
import upc.edu.pe.ecochips.Entities.SoporteSolicitud;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.ISoporteSolicitudService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/soporte")
public class SoporteSolicitudController {
    @Autowired
    private ISoporteSolicitudService tsS;

    @Autowired
    private IUserRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SoporteSolicitudDTO> listar() {
        return tsS.list().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> insertar(@RequestBody SoporteSolicitudDTO dto,
                                           @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            System.out.println("🔐 Token recibido: " + authHeader); // ✅ DEBUG
            System.out.println("🔍 Backend - Recibiendo solicitud de soporte:");
            // ... resto del código
            System.out.println("🔍 Backend - Recibiendo solicitud de soporte:");
            System.out.println("  📋 Título: " + dto.getTitulo());
            System.out.println("  📝 Descripción: " + (dto.getDescripcion() != null ? dto.getDescripcion().substring(0, Math.min(50, dto.getDescripcion().length())) + "..." : "null"));
            System.out.println("  📅 Fecha: " + dto.getFechahora());
            System.out.println("  📂 Apartado: " + dto.getApartado());
            System.out.println("  👤 ID Usuario: " + dto.getIdUsuario());

            ModelMapper m = new ModelMapper();
            SoporteSolicitud so = m.map(dto, SoporteSolicitud.class);

            // ASIGNAR USUARIO
            if (dto.getIdUsuario() != 0) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
                if (usuario != null) {
                    so.setUsuario(usuario);
                    System.out.println("  ✅ Usuario asignado: " + usuario.getNombre() + " (" + usuario.getCorreo() + ")");
                } else {
                    System.out.println("  ❌ Usuario no encontrado con ID: " + dto.getIdUsuario());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("No existe un usuario con el ID: " + dto.getIdUsuario());
                }
            } else {
                System.out.println("  ❌ ID de usuario es 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Debe proporcionar un ID de usuario válido");
            }

            tsS.insert(so);
            System.out.println("  ✅ Solicitud registrada correctamente con ID: " + so.getIdSoporteSolicitud());
            return ResponseEntity.ok("Solicitud registrada correctamente");

        } catch (Exception e) {
            System.out.println("  ❌ Error al registrar solicitud:");
            System.out.println("     Mensaje: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        SoporteSolicitud r = tsS.listID(id);
        if (r == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una solicitud con el ID " + id);
        }
        SoporteSolicitudDTO dto = convertirADTO(r);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        SoporteSolicitud r = tsS.listID(id);
        if (r == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID " + id);
        }
        tsS.delete(id);
        return ResponseEntity.ok("Registro con Id " + id + " eliminado correctamente");
    }



    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> modificar(@RequestBody SoporteSolicitudDTO dto) {
        SoporteSolicitud existente = tsS.listID(dto.getIdSoporteSolicitud());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID " + dto.getIdSoporteSolicitud());
        }

        try {
            ModelMapper m = new ModelMapper();
            SoporteSolicitud r = m.map(dto, SoporteSolicitud.class);

            // ASIGNAR USUARIO
            if (dto.getIdUsuario() != 0) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
                if (usuario != null) {
                    r.setUsuario(usuario);
                }
            } else {
                r.setUsuario(existente.getUsuario());
            }

            tsS.update(r);
            return ResponseEntity.ok("Registro con Id " + dto.getIdSoporteSolicitud() + " modificado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/{titulo}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SoporteSolicitudDTO> listarBuscar(@PathVariable("titulo") String titulo) {
        return tsS.buscarSoporteSolicitud(titulo).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SoporteSolicitudDTO> listarPorEstado(@PathVariable("estado") String estado) {
        return tsS.listarPorEstado(estado).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @GetMapping("/reportes/por-apartado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SoporteApartadoDTO>> obtenerSolicitudesPorApartado() {
        try {
            List<SoporteApartadoDTO> solicitudes = tsS.findSolicitudesPorApartado();
            return ResponseEntity.ok(solicitudes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    private SoporteSolicitudDTO convertirADTO(SoporteSolicitud solicitud) {
        SoporteSolicitudDTO dto = new SoporteSolicitudDTO();
        dto.setIdSoporteSolicitud(solicitud.getIdSoporteSolicitud());
        dto.setTitulo(solicitud.getTitulo());
        dto.setDescripcion(solicitud.getDescripcion());
        dto.setFechahora(solicitud.getFechahora());
        dto.setApartado(solicitud.getApartado());

        if (solicitud.getUsuario() != null) {
            dto.setIdUsuario(solicitud.getUsuario().getIdUsuario());
        }

        return dto;
    }
}



