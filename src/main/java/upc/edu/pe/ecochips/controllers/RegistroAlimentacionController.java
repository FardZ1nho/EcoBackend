package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RegistroAlimentacionDTO;
import upc.edu.pe.ecochips.Entities.Alimento;
import upc.edu.pe.ecochips.Entities.RegistroAlimentacion;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IAlimentoRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRegistroAlimentacionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registrosalimentacion")
public class RegistroAlimentacionController {

    @Autowired
    private IRegistroAlimentacionService raS;

    @Autowired
    private IUserRepository usuarioRepository;

    @Autowired
    private IAlimentoRepository alimentoRepository;

    // ✅ SOLO ADMIN puede ver todos los registros
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegistroAlimentacionDTO> listar() {
        return raS.list().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // ✅ ADMIN o USER pueden ver (simplificado)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        RegistroAlimentacion registro = raS.listId(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de alimentación con el ID: " + id);
        }
        RegistroAlimentacionDTO dto = convertirADTO(registro);
        return ResponseEntity.ok(dto);
    }

    // ✅ ADMIN o USER pueden ver registros por usuario
    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<RegistroAlimentacionDTO> listarPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return raS.listarPorUsuario(idUsuario).stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // ✅ USUARIO puede registrar su alimentación
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> insertar(@RequestBody RegistroAlimentacionDTO dto) {
        try {
            ModelMapper m = new ModelMapper();
            RegistroAlimentacion registro = m.map(dto, RegistroAlimentacion.class);

            // ASIGNAR USUARIO
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No existe un usuario con el ID: " + dto.getIdUsuario());
            }
            registro.setUsuario(usuario);

            // ASIGNAR ALIMENTO
            Alimento alimento = alimentoRepository.findById(dto.getIdAlimento()).orElse(null);
            if (alimento == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No existe un alimento con el ID: " + dto.getIdAlimento());
            }
            registro.setAlimento(alimento);

            raS.insert(registro);
            return ResponseEntity.ok("Registro de alimentación guardado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar alimentación: " + e.getMessage());
        }
    }

    // ✅ ADMIN o USER pueden actualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> actualizar(@RequestBody RegistroAlimentacionDTO dto) {
        RegistroAlimentacion existe = raS.listId(dto.getIdRegistroAlimentacion());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + dto.getIdRegistroAlimentacion());
        }

        try {
            ModelMapper m = new ModelMapper();
            RegistroAlimentacion registro = m.map(dto, RegistroAlimentacion.class);

            // ASIGNAR USUARIO
            if (dto.getIdUsuario() != 0) {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
                if (usuario != null) {
                    registro.setUsuario(usuario);
                }
            } else {
                registro.setUsuario(existe.getUsuario());
            }

            // ASIGNAR ALIMENTO
            if (dto.getIdAlimento() != 0) {
                Alimento alimento = alimentoRepository.findById(dto.getIdAlimento()).orElse(null);
                if (alimento != null) {
                    registro.setAlimento(alimento);
                }
            } else {
                registro.setAlimento(existe.getAlimento());
            }

            raS.update(registro);
            return ResponseEntity.ok("Registro de alimentación con ID " + dto.getIdRegistroAlimentacion() + " modificado correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar registro: " + e.getMessage());
        }
    }

    // ✅ ADMIN o USER pueden eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        RegistroAlimentacion registro = raS.listId(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de alimentación con el ID: " + id);
        }
        raS.delete(id);
        return ResponseEntity.ok("Registro de alimentación con ID " + id + " eliminado correctamente.");
    }

    private RegistroAlimentacionDTO convertirADTO(RegistroAlimentacion registro) {
        RegistroAlimentacionDTO dto = new RegistroAlimentacionDTO();
        dto.setIdRegistroAlimentacion(registro.getIdRegistroAlimentacion());
        dto.setPorciones(registro.getPorciones());
        dto.setCo2Emitido(registro.getCo2Emitido());
        dto.setFecha(registro.getFecha());

        if (registro.getUsuario() != null) {
            dto.setIdUsuario(registro.getUsuario().getIdUsuario());
        }

        if (registro.getAlimento() != null) {
            dto.setIdAlimento(registro.getAlimento().getIdAlimento());
        }

        return dto;
    }
}