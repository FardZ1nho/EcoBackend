package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.DistribucionGeneroDTO;
import upc.edu.pe.ecochips.DTOs.UsuarioImpactoDTO;
import upc.edu.pe.ecochips.Entities.Usuario;
import java.util.List;

public interface IUserService {
    
    // ✅ Agregar solo esta línea
    Usuario registrarUsuario(Usuario usuario);
    
    List<Usuario> list();
    void insert(Usuario usuario);
    Usuario listId(int id);
    void update(Usuario usuario);
    void delete(int id);
    Usuario findByCorreo(String correo);
    Usuario findOneByNombre(String nombre);
    List<Usuario> findByNivel(int nivel);
    Usuario listIdWithCo2(int id);
    List<DistribucionGeneroDTO> obtenerDistribucionParticipantesPorGenero();
    List<UsuarioImpactoDTO> findUsuariosConMenorImpactoAmbiental();
}
