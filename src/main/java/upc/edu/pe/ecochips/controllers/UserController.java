package upc.edu.pe.ecochips.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.*;
import upc.edu.pe.ecochips.Entities.Rol;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IRolRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Usuarios")
@CrossOrigin(origins = "https://ecobackend-1zas.onrender.com")
public class UserController {

    @Autowired
    private IUserService uS;

    @Autowired
    private IRolRepository rolRepository;

    //Listar todos - Solo usuarios autenticados
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UserNoPassDTO> listar(){
        return uS.list().stream().map(y -> {
            ModelMapper m = new ModelMapper();
            UserNoPassDTO dto = m.map(y, UserNoPassDTO.class);
            List<String> nombresRoles = y.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());
            dto.setRoles(nombresRoles);
            return dto;
        }).collect(Collectors.toList());
    }

    //Insertar - Solo ADMIN puede crear usuarios directamente
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> insertar(@RequestBody UserDTO dto) {
        try {
            ModelMapper m = new ModelMapper();
            Usuario u = m.map(dto, Usuario.class);

            if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
                u.getRoles().clear();
                for (String nombreRol : dto.getRoles()) {
                    Rol rolExistente = rolRepository.findByNombre(nombreRol);
                    if (rolExistente != null) {
                        u.addRol(rolExistente);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("El rol '" + nombreRol + "' no existe");
                    }
                }
            } else {
                Rol rolUser = rolRepository.findByNombre("USER");
                if (rolUser != null) u.addRol(rolUser);
            }

            uS.insert(u);
            return ResponseEntity.ok("Usuario insertado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    //Obtener por ID - Solo usuarios autenticados
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Usuario us = uS.listId(id);
        if (us == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe usuario ID: " + id);
        }

        ModelMapper m = new ModelMapper();
        UserNoPassDTO dto = m.map(us, UserNoPassDTO.class);
        List<String> nombresRoles = us.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        dto.setRoles(nombresRoles);
        return ResponseEntity.ok(dto);
    }

    //Eliminar - Solo ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        if (uS.listId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe usuario ID: " + id);
        }
        uS.delete(id);
        return ResponseEntity.ok("Eliminado correctamente.");
    }

    //Modificar - Solo ADMIN o el mismo usuario
    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or #dto.idUsuario == authentication.principal.username")
    public ResponseEntity<String> modificar(@RequestBody UserDTO dto) {
        try {
            if (uS.listId(dto.getIdUsuario()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe usuario ID: " + dto.getIdUsuario());
            }

            ModelMapper m = new ModelMapper();
            Usuario u = m.map(dto, Usuario.class);

            if (dto.getRoles() != null) {
                u.getRoles().clear();
                for (String nombreRol : dto.getRoles()) {
                    Rol rolExistente = rolRepository.findByNombre(nombreRol);
                    if (rolExistente != null) u.addRol(rolExistente);
                }
            }

            uS.update(u);
            return ResponseEntity.ok("Modificado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    //COMPLETAR PERFIL - Actualizar edad y género
    @PutMapping("/completar-perfil/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> completarPerfil(@PathVariable("id") int id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuarioExistente = uS.listId(id);

            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado");
            }

            // Actualizar solo los campos que vienen del formulario
            usuarioExistente.setEdad(usuarioActualizado.getEdad());
            usuarioExistente.setGenero(usuarioActualizado.getGenero());

            uS.update(usuarioExistente);
            return ResponseEntity.ok("Perfil completado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al completar perfil: " + e.getMessage());
        }
    }

    //Buscar por correo - Solo usuarios autenticados
    @GetMapping("/correo/{correo}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarPorCorreo(@PathVariable("correo") String correo) {
        Usuario usuario = uS.findByCorreo(correo);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe correo: " + correo);
        }
        ModelMapper m = new ModelMapper();
        UserNoPassDTO dto = m.map(usuario, UserNoPassDTO.class);
        List<String> nombresRoles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        dto.setRoles(nombresRoles);
        return ResponseEntity.ok(dto);
    }

    //Buscar por nombre - Solo usuarios autenticados
    @GetMapping("/nombre/{nombre}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> buscarPorNombre(@PathVariable("nombre") String nombre) {
        Usuario usuario = uS.findOneByNombre(nombre);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe usuario: " + nombre);
        }
        ModelMapper m = new ModelMapper();
        UserNoPassDTO dto = m.map(usuario, UserNoPassDTO.class);
        List<String> nombresRoles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        dto.setRoles(nombresRoles);
        return ResponseEntity.ok(dto);
    }

    //Listar por nivel - Solo usuarios autenticados
    @GetMapping("/nivel/{nivel}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> listarPorNivel(@PathVariable("nivel") int nivel) {
        List<Usuario> usuarios = uS.findByNivel(nivel);
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay usuarios nivel " + nivel);
        }

        List<UserNoPassDTO> listaDTO = usuarios.stream().map(u -> {
            ModelMapper m = new ModelMapper();
            UserNoPassDTO dto = m.map(u, UserNoPassDTO.class);
            List<String> nombresRoles = u.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());
            dto.setRoles(nombresRoles);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

    //Reportes - Solo ADMIN
    @GetMapping("/reporte/participantes-genero")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obtenerDistribucionParticipantesPorGenero() {
        List<DistribucionGeneroDTO> resultados = uS.obtenerDistribucionParticipantesPorGenero(); // ✅ Usa DTO
        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay datos");
        }
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/reportes/menor-impacto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioImpactoDTO>> obtenerUsuariosConMenorImpactoAmbiental() {
        return ResponseEntity.ok(uS.findUsuariosConMenorImpactoAmbiental());
    }
}
