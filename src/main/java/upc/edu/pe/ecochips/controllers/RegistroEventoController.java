package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RegistroEventoDTO;
import upc.edu.pe.ecochips.Entities.Evento;
import upc.edu.pe.ecochips.Entities.RegistroEvento;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IEventoRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRegistroEventoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registroeventos")
public class RegistroEventoController {

    @Autowired
    private IRegistroEventoService reS;

    @Autowired
    private IEventoRepository eventoRepository;

    @Autowired
    private IUserRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegistroEventoDTO> listar() {
        return reS.list().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        RegistroEvento registro = reS.listId(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de evento con el ID: " + id);
        }
        RegistroEventoDTO dto = convertirADTO(registro);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> insertar(@RequestBody RegistroEventoDTO dto) {
        try {
            // ✅ VALIDACIÓN 1: Verificar que el evento existe
            Evento evento = eventoRepository.findById(dto.getIdEvento()).orElse(null);
            if (evento == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No existe un evento con el ID: " + dto.getIdEvento());
            }

            // ✅ VALIDACIÓN 2: Verificar que el usuario existe
            Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No existe un usuario con el ID: " + dto.getIdUsuario());
            }

            // ✅ AUTOMATIZAR: Fecha de registro = fecha actual
            LocalDate fechaRegistroActual = LocalDate.now();

            // ✅ VALIDACIÓN 3: Verificar que la fecha de registro NO sea después del evento
            LocalDate fechaEvento = evento.getFecha();

            if (fechaRegistroActual.isAfter(fechaEvento)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No puedes registrarte en un evento que ya pasó. " +
                                "Fecha de registro: " + fechaRegistroActual +
                                ", Fecha del evento: " + fechaEvento);
            }

            // ✅ VALIDACIÓN 4: Verificar que no esté registrado duplicado
            boolean yaRegistrado = reS.list().stream()
                    .anyMatch(reg -> reg.getUsuario().getIdUsuario() == dto.getIdUsuario() &&
                            reg.getEvento().getIdEvento() == dto.getIdEvento());

            if (yaRegistrado) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Ya estás registrado en este evento");
            }

            // ✅ TODO VALIDADO - Crear el registro con fecha automática
            ModelMapper m = new ModelMapper();
            RegistroEvento registro = m.map(dto, RegistroEvento.class);
            registro.setEvento(evento);
            registro.setUsuario(usuario);
            registro.setFechaRegistro(fechaRegistroActual); // ← FECHA AUTOMÁTICA

            reS.insert(registro);
            return ResponseEntity.ok("Registro de evento guardado correctamente - Fecha: " + fechaRegistroActual);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar en el evento: " + e.getMessage());
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> actualizar(@RequestBody RegistroEventoDTO dto) {
        try {
            RegistroEvento existe = reS.listId(dto.getIdRegistroEvento());
            if (existe == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se puede modificar. No existe un registro con el ID: " + dto.getIdRegistroEvento());
            }

            // ✅ En actualización, mantener la fecha original (no cambiar)
            LocalDate fechaRegistro = existe.getFechaRegistro();

            // ✅ VALIDACIÓN: Verificar fechas si se cambia el evento
            if (dto.getIdEvento() != 0 && dto.getIdEvento() != existe.getEvento().getIdEvento()) {
                Evento nuevoEvento = eventoRepository.findById(dto.getIdEvento()).orElse(null);
                if (nuevoEvento != null) {
                    LocalDate fechaEvento = nuevoEvento.getFecha();

                    if (fechaRegistro.isAfter(fechaEvento)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("No puedes cambiar a un evento que ya pasó cuando te registraste. " +
                                        "Fecha de registro original: " + fechaRegistro +
                                        ", Fecha del nuevo evento: " + fechaEvento);
                    }
                }
            }

            ModelMapper m = new ModelMapper();
            RegistroEvento registro = m.map(dto, RegistroEvento.class);

            // Mantener la fecha de registro original
            registro.setFechaRegistro(fechaRegistro);

            // Mantener las relaciones si no se especifican nuevas
            if (dto.getIdEvento() == 0) {
                registro.setEvento(existe.getEvento());
            } else {
                Evento evento = eventoRepository.findById(dto.getIdEvento()).orElse(null);
                if (evento != null) {
                    registro.setEvento(evento);
                }
            }

            if (dto.getIdUsuario() == 0) {
                registro.setUsuario(existe.getUsuario());
            } else {
                Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElse(null);
                if (usuario != null) {
                    registro.setUsuario(usuario);
                }
            }

            reS.update(registro);
            return ResponseEntity.ok("Registro de evento con ID " + dto.getIdRegistroEvento() + " modificado correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar registro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        RegistroEvento registro = reS.listId(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro de evento con el ID: " + id);
        }
        reS.delete(id);
        return ResponseEntity.ok("Registro de evento con ID " + id + " eliminado correctamente.");
    }

    @GetMapping("/contar/{idEvento}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> contarPersonasPorEvento(@PathVariable("idEvento") int idEvento) {
        long cantidad = reS.contarPersonasPorEvento(idEvento);
        return ResponseEntity.ok("Cantidad de personas registradas en el evento: " + cantidad);
    }

    private RegistroEventoDTO convertirADTO(RegistroEvento registro) {
        RegistroEventoDTO dto = new RegistroEventoDTO();
        dto.setIdRegistroEvento(registro.getIdRegistroEvento());

        // Mapear manualmente los IDs de las relaciones
        if (registro.getUsuario() != null) {
            dto.setIdUsuario(registro.getUsuario().getIdUsuario());
        }

        if (registro.getEvento() != null) {
            dto.setIdEvento(registro.getEvento().getIdEvento());
        }

        // ❌ ELIMINADO: dto.setFechaRegistro(registro.getFechaRegistro());
        return dto;
    }
}