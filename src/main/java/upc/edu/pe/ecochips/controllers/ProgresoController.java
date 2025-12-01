package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.ProgresoDTO;
import upc.edu.pe.ecochips.Entities.Progreso;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.ServiceInterfaces.IProgresoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/progreso")
public class ProgresoController {

    @Autowired
    private IProgresoService pS;

    // ✅ SOLO ADMIN puede ver todos los progresos
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProgresoDTO> listar() {
        return pS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, ProgresoDTO.class);
        }).collect(Collectors.toList());
    }

    // ✅ ADMIN o USER pueden ver (simplificado)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        Progreso progreso = pS.listId(id);
        if (progreso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un progreso con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        ProgresoDTO dto = m.map(progreso, ProgresoDTO.class);
        return ResponseEntity.ok(dto);
    }

    // ✅ USUARIO puede registrar su progreso
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String insertar(@RequestBody ProgresoDTO dto) {
        ModelMapper m = new ModelMapper();
        Progreso progreso = m.map(dto, Progreso.class);
        Usuario u = new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        progreso.setUsuario(u);
        progreso.setIdProgreso(null);
        pS.insert(progreso);
        return "Progreso registrado correctamente";
    }

    // ✅ ADMIN o USER pueden actualizar (simplificado)
    @PutMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody ProgresoDTO dto) {
        Progreso existe = pS.listId(dto.getIdProgreso());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un progreso con el ID: " + dto.getIdProgreso());
        }
        ModelMapper m = new ModelMapper();
        Progreso progreso = m.map(dto, Progreso.class);
        pS.update(progreso);
        return ResponseEntity.ok("Progreso con ID " + dto.getIdProgreso() + " modificado correctamente.");
    }

    // ✅ SOLO ADMIN puede eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Progreso progreso = pS.listId(id);
        if (progreso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un progreso con el ID: " + id);
        }
        pS.delete(id);
        return ResponseEntity.ok("Progreso con ID " + id + " eliminado correctamente.");
    }

    // ✅ ADMIN o USER pueden ver (simplificado)
    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        Progreso progreso = pS.obtenerProgresoPorUsuario(idUsuario);
        if (progreso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe progreso para el usuario con ID: " + idUsuario);
        }
        ModelMapper m = new ModelMapper();
        ProgresoDTO dto = m.map(progreso, ProgresoDTO.class);
        return ResponseEntity.ok(dto);
    }

    // ✅ SOLO ADMIN puede agregar puntos
    @PostMapping("/agregar-puntos/{idUsuario}/{puntos}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> agregarPuntos(
            @PathVariable("idUsuario") Integer idUsuario,
            @PathVariable("puntos") Integer puntos) {
        pS.agregarPuntos(idUsuario, puntos);
        return ResponseEntity.ok(puntos + " puntos agregados correctamente al usuario ID: " + idUsuario);
    }

    // ✅ ADMIN o USER pueden cambiar estado
    @PostMapping("/cambiar-estado/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> cambiarEstado(
            @PathVariable("idUsuario") Integer idUsuario,
            @RequestBody String estado) {
        pS.cambiarEstado(idUsuario, estado);
        return ResponseEntity.ok("Estado cambiado a: " + estado + " para el usuario ID: " + idUsuario);
    }
}