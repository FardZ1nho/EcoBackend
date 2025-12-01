package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.RetoPopularDTO;
import upc.edu.pe.ecochips.Entities.Reto;
import java.util.List;

public interface IRetoService {
    List<Reto> list();
    void insert(Reto reto);
    Reto listId(int id);
    void delete(int id);
    void update(Reto reto);

    // ✅ NUEVOS MÉTODOS
    List<Reto> listarRetosActivos();
    List<Reto> listarRetosExpirados();


    List<RetoPopularDTO> obtenerRetosMasPopulares();
}