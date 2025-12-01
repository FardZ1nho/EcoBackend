package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RecompensaCostoDTO;
import upc.edu.pe.ecochips.DTOs.RecompensaDTO;
import upc.edu.pe.ecochips.Entities.Recompensa;
import upc.edu.pe.ecochips.ServiceInterfaces.IRecompensaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recompensas")
public class RecompensaController {

    @Autowired
    private IRecompensaService rS;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<RecompensaDTO> listar() {
        return rS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RecompensaDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        Recompensa recompensa = rS.listId(id);
        if (recompensa == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una recompensa con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        RecompensaDTO dto = m.map(recompensa, RecompensaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/costo/{costoCanjes}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<RecompensaDTO> buscarPorCosto(@PathVariable("costoCanjes") int costoCanjes) {
        return rS.buscarPorCostoCanjes(costoCanjes).stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RecompensaDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/titulo/{titulo}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<RecompensaDTO> buscarPorTitulo(@PathVariable("titulo") String titulo) {
        return rS.buscarPorTitulo(titulo).stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RecompensaDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String insertar(@RequestBody RecompensaDTO dto) {
        ModelMapper m = new ModelMapper();
        Recompensa recompensa = m.map(dto, Recompensa.class);
        rS.insert(recompensa);
        return "Recompensa registrada correctamente";
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody RecompensaDTO dto) {
        Recompensa existe = rS.listId(dto.getIdRecompensa());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe una recompensa con el ID: " + dto.getIdRecompensa());
        }
        ModelMapper m = new ModelMapper();
        Recompensa recompensa = m.map(dto, Recompensa.class);
        rS.update(recompensa);
        return ResponseEntity.ok("Recompensa con ID " + dto.getIdRecompensa() + " modificada correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Recompensa recompensa = rS.listId(id);
        if (recompensa == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una recompensa con el ID: " + id);
        }
        rS.delete(id);
        return ResponseEntity.ok("Recompensa con ID " + id + " eliminada correctamente.");
    }

    @GetMapping("/reportes/mas-costosas")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<RecompensaCostoDTO>> obtenerRecompensasMasCostosas() {
        try {
            List<RecompensaCostoDTO> recompensas = rS.findAllRecompensasMasCostosas();
            return ResponseEntity.ok(recompensas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/reportes/mas-baratas")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<RecompensaCostoDTO>> obtenerRecompensasMasBaratas() {
        try {
            List<RecompensaCostoDTO> recompensas = rS.findAllRecompensasMasBaratas();
            return ResponseEntity.ok(recompensas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}