package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.AlimentoDTO;
import upc.edu.pe.ecochips.DTOs.TipoAlimentoCO2DTO;
import upc.edu.pe.ecochips.Entities.Alimento;
import upc.edu.pe.ecochips.ServiceInterfaces.IAlimentoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    @Autowired
    private IAlimentoService aS;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<AlimentoDTO> listar() {
        return aS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, AlimentoDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        Alimento alimento = aS.listId(id);
        if (alimento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un alimento con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        AlimentoDTO dto = m.map(alimento, AlimentoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String insertar(@RequestBody AlimentoDTO dto) {
        ModelMapper m = new ModelMapper();
        Alimento alimento = m.map(dto, Alimento.class);
        aS.insert(alimento);
        return "Alimento registrado correctamente";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody AlimentoDTO dto) {
        Alimento existe = aS.listId(dto.getIdAlimento());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un alimento con el ID: " + dto.getIdAlimento());
        }
        ModelMapper m = new ModelMapper();
        Alimento alimento = m.map(dto, Alimento.class);
        aS.update(alimento);
        return ResponseEntity.ok("Alimento con ID " + dto.getIdAlimento() + " modificado correctamente.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Alimento alimento = aS.listId(id);
        if (alimento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un alimento con el ID: " + id);
        }
        aS.delete(id);
        return ResponseEntity.ok("Alimento con ID " + id + " eliminado correctamente.");
    }

    @GetMapping("/reportes/promedio-co2-tipo")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<TipoAlimentoCO2DTO>> obtenerPromedioCO2PorTipo() {
        try {
            List<TipoAlimentoCO2DTO> resultados = aS.findPromedioCO2PorTipoAlimento();
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}