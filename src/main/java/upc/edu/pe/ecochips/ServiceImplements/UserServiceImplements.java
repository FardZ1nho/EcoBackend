package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upc.edu.pe.ecochips.DTOs.DistribucionGeneroDTO;
import upc.edu.pe.ecochips.DTOs.UsuarioImpactoDTO;
import upc.edu.pe.ecochips.Entities.Rol;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IRegistroAlimentacionRepository;
import upc.edu.pe.ecochips.Repositories.IRegistroTransporteRepository;
import upc.edu.pe.ecochips.Repositories.IRolRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IUserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplements implements IUserService {

    @Autowired
    private IUserRepository uR;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRegistroAlimentacionRepository registroAlimentacionRepository;

    @Autowired
    private IRegistroTransporteRepository registroTransporteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // ==========================================
    // ✅ MÉTODO DE REGISTRO CORREGIDO
    // ==========================================
    @Override
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar si el correo ya existe
        Usuario usuarioExistente = uR.findByCorreo(usuario.getCorreo());
        if (usuarioExistente != null) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // ✅ Buscar el rol USUARIO (ID = 1)
        Rol rolUsuario = rolRepository.findById(1)
            .orElseThrow(() -> new RuntimeException("Rol USUARIO (ID=1) no encontrado"));

        // ✅ SOLUCIÓN: Merge del rol para que esté en el contexto de persistencia
        Rol rolManaged = entityManager.merge(rolUsuario);

        // Limpiar y agregar el rol
        usuario.getRoles().clear();
        usuario.addRol(rolManaged);
        usuario.setEnabled(true);

        return uR.save(usuario);
    }

    @Override
    public List<Usuario> list() {
        List<Usuario> usuarios = uR.findAll();
        return usuarios.stream()
                .map(this::calcularCo2ParaUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(Usuario usuario) {
        uR.save(usuario);
    }

    @Override
    public Usuario listId(int id) {
        Usuario usuario = uR.findById(id).orElse(null);
        return calcularCo2ParaUsuario(usuario);
    }

    @Override
    public Usuario listIdWithCo2(int id) {
        return listId(id);
    }

    @Override
    public void update(Usuario usuario) {
        uR.save(usuario);
    }

    @Override
    public void delete(int id) {
        uR.deleteById(id);
    }

    @Override
    public Usuario findByCorreo(String correo) {
        Usuario usuario = uR.findByCorreo(correo);
        return calcularCo2ParaUsuario(usuario);
    }

    @Override
    public Usuario findOneByNombre(String nombre) {
        Usuario usuario = uR.findOneByNombre(nombre);
        return calcularCo2ParaUsuario(usuario);
    }

    @Override
    public List<Usuario> findByNivel(int nivel) {
        List<Usuario> usuarios = uR.findByNivel(nivel);
        return usuarios.stream()
                .map(this::calcularCo2ParaUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public List<DistribucionGeneroDTO> obtenerDistribucionParticipantesPorGenero() {
        List<Object[]> resultados = uR.obtenerDistribucionParticipantesPorGenero();

        return resultados.stream()
                .map(result -> new DistribucionGeneroDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue(),
                        ((Number) result[2]).doubleValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioImpactoDTO> findUsuariosConMenorImpactoAmbiental() {
        List<Object[]> results = uR.findUsuariosConMenorImpactoAmbiental();
        return results.stream()
                .map(result -> new UsuarioImpactoDTO(
                        (String) result[0],
                        (Double) result[1],
                        (Integer) result[2]
                ))
                .collect(Collectors.toList());
    }

    private Usuario calcularCo2ParaUsuario(Usuario usuario) {
        if (usuario == null) return null;

        Double co2Alimentacion = registroAlimentacionRepository
                .sumarCo2EmitidoPorUsuario(usuario.getIdUsuario());
        Double co2Transporte = registroTransporteRepository
                .sumarCo2EmitidoPorUsuario(usuario.getIdUsuario());

        if (co2Alimentacion == null) co2Alimentacion = 0.0;
        if (co2Transporte == null) co2Transporte = 0.0;

        usuario.setCo2Total(co2Alimentacion + co2Transporte);
        return usuario;
    }
}
