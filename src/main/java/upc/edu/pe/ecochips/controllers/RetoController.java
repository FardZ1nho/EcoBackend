package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.RetoDTO;
import upc.edu.pe.ecochips.DTOs.RetoPopularDTO;
import upc.edu.pe.ecochips.Entities.Reto;
import upc.edu.pe.ecochips.ServiceInterfaces.IRetoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retos")
public class RetoController {

    @Autowired
    private IRetoService rS;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RetoDTO> listar() {
        // ✅ Solo retos activos (no expirados)
        return rS.listarRetosActivos().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RetoDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") int id) {
        Reto reto = rS.listId(id);
        if (reto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un reto con el ID: " + id);
        }

        // ✅ Verificar si el reto está expirado
        if (reto.getFechaFin().isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este reto ya expiró. Fecha fin: " + reto.getFechaFin());
        }

        ModelMapper m = new ModelMapper();
        RetoDTO dto = m.map(reto, RetoDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> insertar(@RequestBody RetoDTO dto) {
        try {
            // ✅ VALIDACIÓN: Fecha fin debe ser después de fecha inicio
            if (dto.getFechaFin().isBefore(dto.getFechaInicio()) ||
                    dto.getFechaFin().isEqual(dto.getFechaInicio())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La fecha fin debe ser posterior a la fecha inicio");
            }

            // ✅ VALIDACIÓN: No crear retos en el pasado
            if (dto.getFechaInicio().isBefore(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No puedes crear retos con fecha inicio en el pasado");
            }

            ModelMapper m = new ModelMapper();
            Reto reto = m.map(dto, Reto.class);
            rS.insert(reto);
            return ResponseEntity.ok("Reto registrado correctamente. Duración: " +
                    dto.getFechaInicio() + " hasta " + dto.getFechaFin());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar reto: " + e.getMessage());
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody RetoDTO dto) {
        Reto existe = rS.listId(dto.getIdReto());
        if (existe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un reto con el ID: " + dto.getIdReto());
        }

        try {
            // ✅ VALIDACIÓN: Fecha fin debe ser después de fecha inicio
            if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La fecha fin debe ser posterior a la fecha inicio");
            }

            ModelMapper m = new ModelMapper();
            Reto reto = m.map(dto, Reto.class);
            rS.update(reto);
            return ResponseEntity.ok("Reto con ID " + dto.getIdReto() + " modificado correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar reto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        Reto reto = rS.listId(id);
        if (reto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un reto con el ID: " + id);
        }
        rS.delete(id);
        return ResponseEntity.ok("Reto con ID " + id + " eliminado correctamente.");
    }

    //ENDPOINT EXTRA: Listar todos los retos (incluyendo expirados - para admin)
    @GetMapping("/admin/todos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<RetoDTO> listarTodos() {
        return rS.list().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RetoDTO.class);
        }).collect(Collectors.toList());
    }

    //ENDPOINT EXTRA: Retos expirados
    @GetMapping("/admin/expirados")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<RetoDTO> listarExpirados() {
        return rS.listarRetosExpirados().stream().map(y->{
            ModelMapper m = new ModelMapper();
            return m.map(y, RetoDTO.class);
        }).collect(Collectors.toList());
    }

    //NUEVO ENDPOINT: Reporte de retos más populares
    @GetMapping("/reportes/populares")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<RetoPopularDTO>> obtenerRetosMasPopulares() {
        try {
            List<RetoPopularDTO> retos = rS.obtenerRetosMasPopulares();
            return ResponseEntity.ok(retos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}