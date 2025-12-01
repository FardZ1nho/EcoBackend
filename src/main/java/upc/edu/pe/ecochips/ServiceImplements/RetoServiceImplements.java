package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.RetoPopularDTO;
import upc.edu.pe.ecochips.Entities.Reto;
import upc.edu.pe.ecochips.Repositories.IRetoRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRetoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetoServiceImplements implements IRetoService {

    @Autowired
    private IRetoRepository rR;

    @Override
    public List<Reto> list() {
        return rR.findAll();
    }

    @Override
    public void insert(Reto reto) {
        rR.save(reto);
    }

    @Override
    public Reto listId(int id) {
        return rR.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        rR.deleteById(id);
    }

    @Override
    public void update(Reto reto) {
        rR.save(reto);
    }

    // ✅ NUEVO: Solo retos activos (no expirados)
    @Override
    public List<Reto> listarRetosActivos() {
        LocalDate hoy = LocalDate.now();
        return rR.findAll().stream()
                .filter(reto -> reto.getFechaFin().isAfter(hoy) ||
                        reto.getFechaFin().isEqual(hoy))
                .collect(Collectors.toList());
    }

    // ✅ NUEVO: Retos expirados
    @Override
    public List<Reto> listarRetosExpirados() {
        LocalDate hoy = LocalDate.now();
        return rR.findAll().stream()
                .filter(reto -> reto.getFechaFin().isBefore(hoy))
                .collect(Collectors.toList());
    }

    // ✅ ELIMINACIÓN AUTOMÁTICA: Se ejecuta cada día a las 2 AM
    @Scheduled(cron = "0 0 2 * * ?") // 2:00 AM todos los días
    public void eliminarRetosExpirados() {
        LocalDate hoy = LocalDate.now();
        List<Reto> retosExpirados = rR.findAll().stream()
                .filter(reto -> reto.getFechaFin().isBefore(hoy))
                .collect(Collectors.toList());

        if (!retosExpirados.isEmpty()) {
            rR.deleteAll(retosExpirados);
            System.out.println("✅ Eliminados " + retosExpirados.size() + " retos expirados automáticamente");
        }
    }

    @Override
    public List<RetoPopularDTO> obtenerRetosMasPopulares() {
        List<Object[]> results = rR.findRetosMasPopulares(); // ✅ Usa el nombre correcto
        return results.stream()
                .map(result -> new RetoPopularDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}