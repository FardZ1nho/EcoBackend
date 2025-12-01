package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RegistroTransporteDTO;
import upc.edu.pe.ecochips.Entities.RegistroTransporte;
import upc.edu.pe.ecochips.Entities.Transporte;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.ITransporteRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRegistroTransporteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registrostransporte")
public class RegistroTransporteController {

    @Autowired
    private IRegistroTransporteService rtS;

    @Autowired
    private IUserRepository usuarioRepository;

    @Autowired
    private ITransporteRepository transporteRepository;

    // ✅ SOLO ADMIN puede ver todos los registros
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegistroTransporteDTO> listar() {
        return rtS.list().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // ✅ ADMIN o USER pueden ver (simplificado)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        RegistroTransporte registro = rtS.listId(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de transporte con el ID: " + id);
        }
        RegistroTransporteDTO dto = convertirADTO(registro);
        return ResponseEntity.ok(dto);
    }

    // ✅ ADMIN o USER pueden ver registros por usuario
    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<RegistroTransporteDTO> listarPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return rtS.listarPorUsuario(idUsuario).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // ✅ USUARIO puede registrar su transporte
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> insertar(@RequestBody RegistroTransporteDTO dto) {
        try {
            ModelMapper m = new ModelMapper();
            RegistroTransporte registro = m.map(dto, RegistroTransporte.class);

            // ASIGNAR USUARIO
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No existe un usuario con el ID: " + dto.getIdUsuario());
            }
            registro.setUsuario(usuario);

            // ASIGNAR TRANSPORTE
            Transporte transporte = transporteRepository.findById(dto.getIdTransporte()).orElse(null);
            if (transporte == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No existe un transporte con el ID: " + dto.getIdTransporte());
            }
            registro.setTransporte(transporte);

            rtS.insert(registro);
            return ResponseEntity.ok("Registro de transporte guardado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar transporte: " + e.getMessage());
        }
    }

    // ✅ ADMIN o USER pueden actualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> actualizar(@RequestBody RegistroTransporteDTO dto) {
        RegistroTransporte existe = rtS.listId(dto.getIdRegistroTransporte());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + dto.getIdRegistroTransporte());
        }

        try {
            ModelMapper m = new ModelMapper();
            RegistroTransporte registro = m.map(dto, RegistroTransporte.class);

            // ASIGNAR USUARIO
            if (dto.getIdUsuario() != 0) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
                if (usuario != null) {
                    registro.setUsuario(usuario);
                }
            } else {
                registro.setUsuario(existe.getUsuario());
            }

            // ASIGNAR TRANSPORTE
            if (dto.getIdTransporte() != 0) {
                Transporte transporte = transporteRepository.findById(dto.getIdTransporte()).orElse(null);
                if (transporte != null) {
                    registro.setTransporte(transporte);
                }
            } else {
                registro.setTransporte(existe.getTransporte());
            }

            rtS.update(registro);
            return ResponseEntity.ok("Registro de transporte con ID " + dto.getIdRegistroTransporte() + " modificado correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar registro: " + e.getMessage());
        }
    }

    // ✅ ADMIN o USER pueden eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        RegistroTransporte registro = rtS.listId(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de transporte con el ID: " + id);
        }
        rtS.delete(id);
        return ResponseEntity.ok("Registro de transporte con ID " + id + " eliminado correctamente.");
    }

    private RegistroTransporteDTO convertirADTO(RegistroTransporte registro) {
        RegistroTransporteDTO dto = new RegistroTransporteDTO();
        dto.setIdRegistroTransporte(registro.getIdRegistroTransporte());
        dto.setDistanciaKm(registro.getDistanciaKm());
        dto.setCo2Emitido(registro.getCo2Emitido());
        dto.setFecha(registro.getFecha());

        if (registro.getUsuario() != null) {
            dto.setIdUsuario(registro.getUsuario().getIdUsuario());
        }

        if (registro.getTransporte() != null) {
            dto.setIdTransporte(registro.getTransporte().getIdTransporte());
        }

        return dto;
    }
}