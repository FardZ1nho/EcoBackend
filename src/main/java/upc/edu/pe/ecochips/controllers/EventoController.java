package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.EventoDTO;
import upc.edu.pe.ecochips.Entities.Evento;
import upc.edu.pe.ecochips.ServiceInterfaces.IEventoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private IEventoService eS;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<EventoDTO> listar() {
        return eS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, EventoDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id) {
        Evento evento = eS.listId(id);
        if (evento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un evento con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        EventoDTO dto = m.map(evento, EventoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String insertar(@RequestBody EventoDTO dto) {
        ModelMapper m = new ModelMapper();
        Evento evento = m.map(dto, Evento.class);
        eS.insert(evento);
        return "Evento registrado correctamente";
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody EventoDTO dto) {
        Evento eventoExistente = eS.listId(dto.getIdEvento());
        if (eventoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un evento con el ID: " + dto.getIdEvento());
        }
        ModelMapper m = new ModelMapper();
        Evento eventoActualizado = m.map(dto, Evento.class);
        eS.update(eventoActualizado);
        return ResponseEntity.ok("Evento con ID " + dto.getIdEvento() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Evento evento = eS.listId(id);
        if (evento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un evento con el ID: " + id);
        }
        eS.delete(id);
        return ResponseEntity.ok("Evento con ID " + id + " eliminado correctamente.");
    }
}