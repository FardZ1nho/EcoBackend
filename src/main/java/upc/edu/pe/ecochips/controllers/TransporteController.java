package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.TransporteDTO;
import upc.edu.pe.ecochips.DTOs.TransporteImpactoDTO;
import upc.edu.pe.ecochips.Entities.Transporte;
import upc.edu.pe.ecochips.ServiceInterfaces.ITransporteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transportes")
public class TransporteController {

    @Autowired
    private ITransporteService tS;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<TransporteDTO> listar() {
        return tS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, TransporteDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        Transporte transporte = tS.listId(id);
        if (transporte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un transporte con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        TransporteDTO dto = m.map(transporte, TransporteDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String insertar(@RequestBody TransporteDTO dto) {
        ModelMapper m = new ModelMapper();
        Transporte transporte = m.map(dto, Transporte.class);
        tS.insert(transporte);
        return "Transporte registrado correctamente";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody TransporteDTO dto) {
        // Verificar que el ID no sea 0
        if (dto.getIdTransporte() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El ID del transporte no puede ser 0");
        }

        Transporte existe = tS.listId(dto.getIdTransporte());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un transporte con el ID: " + dto.getIdTransporte());
        }

        ModelMapper m = new ModelMapper();
        Transporte transporte = m.map(dto, Transporte.class);
        tS.update(transporte);
        return ResponseEntity.ok("Transporte con ID " + dto.getIdTransporte() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Transporte transporte = tS.listId(id);
        if (transporte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un transporte con el ID: " + id);
        }
        tS.delete(id);
        return ResponseEntity.ok("Transporte con ID " + id + " eliminado correctamente.");
    }

    // En TransporteController.java
    @GetMapping("/reportes/top5-contaminantes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransporteImpactoDTO>> obtenerTop5TransportesMasContaminantes() {
        try {
            List<TransporteImpactoDTO> transportes = tS.findTop5TransportesMasContaminantes();
            return ResponseEntity.ok(transportes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}