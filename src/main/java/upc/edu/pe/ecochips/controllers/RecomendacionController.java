package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RecomendacionDTO;
import upc.edu.pe.ecochips.DTOs.RecomendacionTipoDTO;
import upc.edu.pe.ecochips.Entities.Recomendacion;
import upc.edu.pe.ecochips.ServiceInterfaces.IRecomendacionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recomendaciones")
public class RecomendacionController {

    @Autowired
    private IRecomendacionService rS;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<RecomendacionDTO> listar() {
        return rS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RecomendacionDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        Recomendacion recomendacion = rS.listId(id);
        if (recomendacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una recomendación con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        RecomendacionDTO dto = m.map(recomendacion, RecomendacionDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String insertar(@RequestBody RecomendacionDTO dto) {
        ModelMapper m = new ModelMapper();
        Recomendacion recomendacion = m.map(dto, Recomendacion.class);
        rS.insert(recomendacion);
        return "Recomendación registrada correctamente";
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody RecomendacionDTO dto) {
        Recomendacion existe = rS.listId(dto.getIdRecomendacion());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe una recomendación con el ID: " + dto.getIdRecomendacion());
        }
        ModelMapper m = new ModelMapper();
        Recomendacion recomendacion = m.map(dto, Recomendacion.class);
        rS.update(recomendacion);
        return ResponseEntity.ok("Recomendación con ID " + dto.getIdRecomendacion() + " modificada correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Recomendacion recomendacion = rS.listId(id);
        if (recomendacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe una recomendación con el ID: " + id);
        }
        rS.delete(id);
        return ResponseEntity.ok("Recomendación con ID " + id + " eliminada correctamente.");
    }

    // En RecomendacionController.java
    @GetMapping("/reportes/por-tipo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<RecomendacionTipoDTO>> obtenerRecomendacionesPorTipo() {
        try {
            List<RecomendacionTipoDTO> recomendaciones = rS.findRecomendacionesPorTipo();
            return ResponseEntity.ok(recomendaciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}