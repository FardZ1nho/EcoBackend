package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RecompensaPopularDTO;
import upc.edu.pe.ecochips.DTOs.UsuarioRecompensaDTO;
import upc.edu.pe.ecochips.Entities.UsuarioRecompensa;
import upc.edu.pe.ecochips.ServiceInterfaces.IUsuarioRecompensaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuariorecompensas")
public class UsuarioRecompensaController {

    @Autowired
    private IUsuarioRecompensaService urS;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UsuarioRecompensaDTO> listar() {
        return urS.list().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        UsuarioRecompensa usuarioRecompensa = urS.listId(id);
        if (usuarioRecompensa == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de usuario-recompensa con el ID: " + id);
        }
        UsuarioRecompensaDTO dto = convertirADTO(usuarioRecompensa);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UsuarioRecompensaDTO> listarPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return urS.listarPorUsuario(idUsuario).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String insertar(@RequestBody UsuarioRecompensaDTO dto) {
        ModelMapper m = new ModelMapper();
        UsuarioRecompensa usuarioRecompensa = m.map(dto, UsuarioRecompensa.class);
        urS.insert(usuarioRecompensa);
        return "Usuario-Recompensa registrado correctamente";
    }

    @PostMapping("/canjear/{idUsuario}/{idRecompensa}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> canjearRecompensa(
            @PathVariable("idUsuario") Integer idUsuario,
            @PathVariable("idRecompensa") Integer idRecompensa) {
        String resultado = urS.canjearRecompensa(idUsuario, idRecompensa);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody UsuarioRecompensaDTO dto) {
        UsuarioRecompensa existe = urS.listId(dto.getIdUsuarioRecompensa());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + dto.getIdUsuarioRecompensa());
        }
        ModelMapper m = new ModelMapper();
        UsuarioRecompensa usuarioRecompensa = m.map(dto, UsuarioRecompensa.class);
        urS.update(usuarioRecompensa);
        return ResponseEntity.ok("Usuario-Recompensa con ID " + dto.getIdUsuarioRecompensa() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        UsuarioRecompensa usuarioRecompensa = urS.listId(id);
        if (usuarioRecompensa == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de usuario-recompensa con el ID: " + id);
        }
        urS.delete(id);
        return ResponseEntity.ok("Usuario-Recompensa con ID " + id + " eliminado correctamente.");
    }

    // MÉTODO DE CONVERSIÓN MANUAL PARA EVITAR CONFLICTOS CON MODELMAPPER
    private UsuarioRecompensaDTO convertirADTO(UsuarioRecompensa usuarioRecompensa) {
        UsuarioRecompensaDTO dto = new UsuarioRecompensaDTO();
        dto.setIdUsuarioRecompensa(usuarioRecompensa.getIdUsuarioRecompensa());

        // Mapear manualmente los IDs de las relaciones
        if (usuarioRecompensa.getUsuario() != null) {
            dto.setIdUsuario(usuarioRecompensa.getUsuario().getIdUsuario());
        }

        if (usuarioRecompensa.getRecompensa() != null) {
            dto.setIdRecompensa(usuarioRecompensa.getRecompensa().getIdRecompensa());
        }

        dto.setFechaAsignacion(usuarioRecompensa.getFechaAsignacion());
        return dto;
    }

    @GetMapping("/reportes/recompensas-populares")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<RecompensaPopularDTO>> obtenerRecompensasMasPopulares() {
        try {
            List<RecompensaPopularDTO> recompensas = urS.findRecompensasMasPopulares();
            return ResponseEntity.ok(recompensas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}