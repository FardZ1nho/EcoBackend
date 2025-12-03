package upc.edu.pe.ecochips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.ecochips.DTOs.JwtRequestDTO;
import upc.edu.pe.ecochips.DTOs.UsuarioRegistroDTO;
import upc.edu.pe.ecochips.Entities.Rol;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IRolRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceImplements.JwtUserDetailsService;
import upc.edu.pe.ecochips.securities.JwtTokenUtil;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ LOGIN - Genera JWT Token (acepta nombre o correo)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDTO req) {
        try {
            // Autenticar con Spring Security
            authenticate(req.getUsername(), req.getPassword());

            // Cargar detalles del usuario
            final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());

            // Generar token JWT
            final String token = jwtTokenUtil.generateToken(userDetails);

            // Obtener información adicional del usuario
            Usuario usuario = userRepository.findOneByNombre(req.getUsername());
            if (usuario == null) {
                usuario = userRepository.findByCorreo(req.getUsername());
            }

            // Validar que se encontró el usuario
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Usuario no encontrado"));
            }

            // Respuesta completa
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", usuario.getNombre());
            response.put("roles", userDetails.getAuthorities());
            response.put("idUsuario", usuario.getIdUsuario());
            response.put("nombre", usuario.getNombre());
            response.put("correo", usuario.getCorreo());
            response.put("nivel", usuario.getNivel());
            response.put("co2Total", usuario.getCo2Total());

            return ResponseEntity.ok(response);

        } catch (DisabledException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Usuario deshabilitado");
            error.put("message", "Tu cuenta ha sido deshabilitada. Contacta al administrador.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);

        } catch (BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            error.put("message", "Correo/usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error en el login");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ✅ REGISTRO - Crea usuario y devuelve token automáticamente
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioRegistroDTO dto) {
        try {
            // 1. Validar si el nombre ya existe
            if (userRepository.existsByNombre(dto.getNombre())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "El nombre de usuario ya existe"));
            }

            // 2. Validar si el correo ya existe
            if (userRepository.countByCorreo(dto.getCorreo()) > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "El correo ya está registrado"));
            }

            // 3. Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));

            // 4. Obtener rol USER desde BD usando findById
            Rol rolUsuario = rolRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Rol USER (ID=1) no encontrado en la base de datos"));

            // Asignar el rol al usuario
            usuario.getRoles().clear();
            usuario.addRol(rolUsuario);

            // 5. Valores por defecto
            usuario.setEnabled(true);
            usuario.setNivel(1);
            usuario.setEdad(0);
            usuario.setGenero("PENDIENTE");
            usuario.setCo2Total(0.0);
            usuario.setCanjesDisponibles(0);

            // 6. Guardar usuario
            Usuario savedUsuario = userRepository.save(usuario);

            // 7. Generar token automáticamente
            final UserDetails userDetails = userDetailsService.loadUserByUsername(savedUsuario.getNombre());
            final String token = jwtTokenUtil.generateToken(userDetails);

            // 8. Crear respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuario registrado exitosamente");
            response.put("token", token);
            response.put("username", savedUsuario.getNombre());
            response.put("correo", savedUsuario.getCorreo());
            response.put("idUsuario", savedUsuario.getIdUsuario());
            response.put("roles", userDetails.getAuthorities());
            response.put("nivel", savedUsuario.getNivel());
            response.put("co2Total", savedUsuario.getCo2Total());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar usuario: " + e.getMessage()));
        }
    }

    // ✅ Método privado para autenticar
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException e) {
            throw new DisabledException("Usuario deshabilitado", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales inválidas", e);
        }
    }
}
