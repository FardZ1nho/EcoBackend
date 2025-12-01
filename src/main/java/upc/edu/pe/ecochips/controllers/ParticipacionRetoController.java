package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.ParticipacionRetoDTO;
import upc.edu.pe.ecochips.DTOs.TopUsuarioDTO;
import upc.edu.pe.ecochips.Entities.ParticipacionReto;
import upc.edu.pe.ecochips.ServiceInterfaces.IParticipacionRetoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participacionretos")
public class ParticipacionRetoController {

    @Autowired
    private IParticipacionRetoService prS;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ParticipacionRetoDTO> listar() {
        return prS.list().stream().map(participacion -> {
            ModelMapper m = new ModelMapper();
            ParticipacionRetoDTO dto = m.map(participacion, ParticipacionRetoDTO.class);

            // Si usas los campos adicionales
            if (participacion.getUsuario() != null) {
                dto.setNombreUsuario(participacion.getUsuario().getNombre()); // ajusta según tu entidad Usuario
            }
            if (participacion.getReto() != null) {
                dto.setTituloReto(participacion.getReto().getTitulo()); // ajusta según tu entidad Reto
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        ParticipacionReto participacion = prS.listId(id);
        if (participacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una participación con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        ParticipacionRetoDTO dto = m.map(participacion, ParticipacionRetoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ParticipacionRetoDTO> listarPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return prS.listarPorUsuario(idUsuario).stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, ParticipacionRetoDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/contar/{idReto}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> contarParticipantesPorReto(@PathVariable("idReto") int idReto) {
        long cantidad = prS.contarParticipantesPorReto(idReto);
        return ResponseEntity.ok("Cantidad de participantes en el reto: " + cantidad);
    }

    @PostMapping("/completar/{idUsuario}/{idReto}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> completarReto(
            @PathVariable("idUsuario") Integer idUsuario,
            @PathVariable("idReto") Integer idReto) {
        String resultado = prS.completarReto(idUsuario, idReto);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String insertar(@RequestBody ParticipacionRetoDTO dto) {
        ModelMapper m = new ModelMapper();
        ParticipacionReto participacion = m.map(dto, ParticipacionReto.class);
        prS.insert(participacion);
        return "Participación en reto registrada correctamente";
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody ParticipacionRetoDTO dto) {
        ParticipacionReto existe = prS.listId(dto.getIdParticipacion());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe una participación con el ID: " + dto.getIdParticipacion());
        }
        ModelMapper m = new ModelMapper();
        ParticipacionReto participacion = m.map(dto, ParticipacionReto.class);
        prS.update(participacion);
        return ResponseEntity.ok("Participación con ID " + dto.getIdParticipacion() + " modificada correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        ParticipacionReto participacion = prS.listId(id);
        if (participacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una participación con el ID: " + id);
        }
        prS.delete(id);
        return ResponseEntity.ok("Participación con ID " + id + " eliminada correctamente. Canje removido si correspondía.");
    }
    // Endpoint para eliminar participación por usuario y reto
    @DeleteMapping("/usuario/{idUsuario}/reto/{idReto}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> eliminarParticipacion(
            @PathVariable("idUsuario") Integer idUsuario,
            @PathVariable("idReto") Integer idReto) {
        String resultado = prS.eliminarParticipacion(idUsuario, idReto);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/reportes/top-usuarios")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<TopUsuarioDTO>> obtenerTopUsuariosConMasRetosCompletados() {
        try {
            List<TopUsuarioDTO> topUsuarios = prS.findTopUsuariosConMasRetosCompletados();
            return ResponseEntity.ok(topUsuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}