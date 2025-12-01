package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.RecompensaPopularDTO;
import upc.edu.pe.ecochips.Entities.Recompensa;
import upc.edu.pe.ecochips.Entities.Usuario;
import upc.edu.pe.ecochips.Entities.UsuarioRecompensa;
import upc.edu.pe.ecochips.Repositories.IRecompensaRepository;
import upc.edu.pe.ecochips.Repositories.IUsuarioRecompensaRepository;
import upc.edu.pe.ecochips.Repositories.IUserRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IUsuarioRecompensaService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioRecompensaServiceImplements implements IUsuarioRecompensaService {

    @Autowired
    private IUsuarioRecompensaRepository urR;

    @Autowired
    private IUserRepository uR;

    @Autowired
    private IRecompensaRepository rR;

    @Override
    public List<UsuarioRecompensa> list() {
        return urR.findAll();
    }

    @Override
    public void insert(UsuarioRecompensa usuarioRecompensa) {
        urR.save(usuarioRecompensa);
    }

    @Override
    public UsuarioRecompensa listId(int id) {
        return urR.findById(id).orElse(null);
    }

    @Override
    public void update(UsuarioRecompensa usuarioRecompensa) {
        urR.save(usuarioRecompensa);
    }

    @Override
    public void delete(int id) {
        urR.deleteById(id);
    }

    @Override
    public List<UsuarioRecompensa> listarPorUsuario(Integer idUsuario) {
        return urR.findByUsuarioId(idUsuario);
    }

    @Override
    public String canjearRecompensa(Integer idUsuario, Integer idRecompensa) {
        Usuario usuario = uR.findById(idUsuario).orElse(null);
        Recompensa recompensa = rR.findById(idRecompensa).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado";
        }
        if (recompensa == null) {
            return "Recompensa no encontrada";
        }

        // Verificar si tiene suficientes canjes
        if (usuario.getCanjesDisponibles() < recompensa.getCostoCanjes()) {
            return "No tienes suficientes canjes para esta recompensa. Necesitas: " + recompensa.getCostoCanjes() + ", tienes: " + usuario.getCanjesDisponibles();
        }

        // Restar canjes al usuario
        usuario.setCanjesDisponibles(usuario.getCanjesDisponibles() - recompensa.getCostoCanjes());
        uR.save(usuario);

        // Asignar recompensa al usuario
        UsuarioRecompensa ur = new UsuarioRecompensa();
        ur.setUsuario(usuario);
        ur.setRecompensa(recompensa);
        ur.setFechaAsignacion(LocalDate.now());
        urR.save(ur);

        return "Canje realizado con éxito. Recompensa: " + recompensa.getTituloRecompensa();
    }

    // En UsuarioRecompensaServiceImplement.java
    @Override
    public List<RecompensaPopularDTO> findRecompensasMasPopulares() {
        List<Object[]> results = urR.findRecompensasMasPopulares();
        return results.stream()
                .map(result -> new RecompensaPopularDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}