package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RolDTO;
import upc.edu.pe.ecochips.Entities.Rol;
import upc.edu.pe.ecochips.ServiceInterfaces.IRolService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Roles")
public class RolController {
    @Autowired
    private IRolService rS;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<RolDTO> listar(){
        return rS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RolDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String insertar(@RequestBody RolDTO dto) {
        ModelMapper m = new ModelMapper();
        Rol r = m.map(dto, Rol.class);
        rS.insert(r);
        return "Rol registrado correctamente";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable ("id") Integer id) {
        Rol rol = rS.listId(id);
        if(rol == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un rol con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        RolDTO dto = m.map(rol, RolDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Rol rol = rS.listId(id);
        if (rol == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un rol con el ID: " + id);
        }
        rS.delete(id);
        return ResponseEntity.ok("Rol con ID " + id + " eliminado correctamente.");
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> modificar(@RequestBody RolDTO dto) {
        Rol existente = rS.listId(dto.getIdRol());
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un rol con el ID: " + dto.getIdRol());
        }
        ModelMapper m = new ModelMapper();
        Rol r = m.map(dto, Rol.class);
        rS.update(r);
        return ResponseEntity.ok("Rol con ID " + dto.getIdRol() + " modificado correctamente.");
    }

    @GetMapping("/buscar/{nombre}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<RolDTO> buscarPorNombre(@PathVariable("nombre") String nombre) {
        return rS.buscarR(nombre).stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RolDTO.class);
        }).collect(Collectors.toList());
    }
}