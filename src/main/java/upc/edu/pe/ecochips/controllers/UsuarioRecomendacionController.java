package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RecomendacionDTO;
import upc.edu.pe.ecochips.DTOs.TipoRecomendacionAsignadoDTO;
import upc.edu.pe.ecochips.DTOs.UsuarioRecomendacionDTO;
import upc.edu.pe.ecochips.Entities.UsuarioRecomendacion;
import upc.edu.pe.ecochips.ServiceInterfaces.IUsuarioRecomendacionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuariorecomendaciones")
public class UsuarioRecomendacionController {

    @Autowired
    private IUsuarioRecomendacionService urS;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UsuarioRecomendacionDTO> listar() {
        return urS.list().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        UsuarioRecomendacion usuarioRecomendacion = urS.listId(id);
        if (usuarioRecomendacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de usuario-recomendación con el ID: " + id);
        }
        UsuarioRecomendacionDTO dto = convertirADTO(usuarioRecomendacion);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{idUsuario}/recomendaciones")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<RecomendacionDTO> listarRecomendacionesPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return urS.listarRecomendacionesPorUsuario(idUsuario).stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RecomendacionDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/filtrar/{tipo}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<RecomendacionDTO> filtrarRecomendacionesPorTipo(@PathVariable("tipo") String tipo) {
        return urS.filtrarRecomendacionesPorTipo(tipo).stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RecomendacionDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String insertar(@RequestBody UsuarioRecomendacionDTO dto) {
        ModelMapper m = new ModelMapper();
        UsuarioRecomendacion usuarioRecomendacion = m.map(dto, UsuarioRecomendacion.class);
        urS.insert(usuarioRecomendacion);
        return "Usuario-Recomendación registrado correctamente";
    }

    @PostMapping("/asignar/{idUsuario}/{idRecomendacion}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> asignarRecomendacion(
            @PathVariable("idUsuario") Integer idUsuario,
            @PathVariable("idRecomendacion") Integer idRecomendacion) {
        String resultado = urS.asignarRecomendacion(idUsuario, idRecomendacion);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody UsuarioRecomendacionDTO dto) {
        UsuarioRecomendacion existe = urS.listId(dto.getIdUsuarioRecomendacion());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + dto.getIdUsuarioRecomendacion());
        }
        ModelMapper m = new ModelMapper();
        UsuarioRecomendacion usuarioRecomendacion = m.map(dto, UsuarioRecomendacion.class);
        urS.update(usuarioRecomendacion);
        return ResponseEntity.ok("Usuario-Recomendación con ID " + dto.getIdUsuarioRecomendacion() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        UsuarioRecomendacion usuarioRecomendacion = urS.listId(id);
        if (usuarioRecomendacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de usuario-recomendación con el ID: " + id);
        }
        urS.delete(id);
        return ResponseEntity.ok("Usuario-Recomendación con ID " + id + " eliminado correctamente.");
    }

    // MÉTODO DE CONVERSIÓN MANUAL PARA EVITAR CONFLICTOS CON MODELMAPPER
    private UsuarioRecomendacionDTO convertirADTO(UsuarioRecomendacion usuarioRecomendacion) {
        UsuarioRecomendacionDTO dto = new UsuarioRecomendacionDTO();
        dto.setIdUsuarioRecomendacion(usuarioRecomendacion.getIdUsuarioRecomendacion());

        // Mapear manualmente los IDs de las relaciones
        if (usuarioRecomendacion.getUsuario() != null) {
            dto.setIdUsuario(usuarioRecomendacion.getUsuario().getIdUsuario());
        }

        if (usuarioRecomendacion.getRecomendacion() != null) {
            dto.setIdRecomendacion(usuarioRecomendacion.getRecomendacion().getIdRecomendacion());
        }

        dto.setFechaAsignacion(usuarioRecomendacion.getFechaAsignacion());
        return dto;

    }

    @GetMapping("/reportes/tipos-mas-asignados")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<TipoRecomendacionAsignadoDTO>> obtenerTiposRecomendacionMasAsignados() {
        try {
            List<TipoRecomendacionAsignadoDTO> tipos = urS.findTiposRecomendacionMasAsignados();
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}