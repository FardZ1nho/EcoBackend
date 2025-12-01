package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Repositories.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = null;

        // ✅ INTENTA BUSCAR POR NOMBRE PRIMERO
        user = repo.findOneByNombre(username);

        // ✅ SI NO LO ENCUENTRA, BUSCA POR CORREO
        if (user == null) {
            user = repo.findByCorreo(username);
        }

        // ✅ SI SIGUE SIN ENCONTRARLO, LANZA EXCEPCIÓN
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre o correo: " + username);
        }

        // ✅ VERIFICAR SI ESTÁ HABILITADO
        if (!user.getEnabled()) {
            throw new UsernameNotFoundException("Usuario deshabilitado: " + username);
        }

        // ✅ CONVERTIR ROLES CORRECTAMENTE
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
                .collect(Collectors.toList());

        // ✅ RETORNAR UserDetails CON EL NOMBRE DEL USUARIO
        return User.builder()
                .username(user.getNombre()) // Siempre usar el NOMBRE como username
                .password(user.getContrasena())
                .authorities(authorities)
                .disabled(!user.getEnabled())
                .build();
    }
}