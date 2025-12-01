package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.RecompensaPopularDTO;
import upc.edu.pe.ecochips.Entities.UsuarioRecompensa;
import java.util.List;

public interface IUsuarioRecompensaService {
    List<UsuarioRecompensa> list();
    void insert(UsuarioRecompensa usuarioRecompensa);
    UsuarioRecompensa listId(int id);
    void update(UsuarioRecompensa usuarioRecompensa);
    void delete(int id);

    // Método específico
    List<UsuarioRecompensa> listarPorUsuario(Integer idUsuario);
    String canjearRecompensa(Integer idUsuario, Integer idRecompensa);

    // Nuevo método para el reporte
    List<RecompensaPopularDTO> findRecompensasMasPopulares();
}