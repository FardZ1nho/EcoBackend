package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.SoporteApartadoDTO;
import upc.edu.pe.ecochips.Entities.SoporteSolicitud;

import java.util.List;

public interface ISoporteSolicitudService {
    public List<SoporteSolicitud> list();
    public void insert(SoporteSolicitud solicitud);
    public SoporteSolicitud listID(int id);
    public void update(SoporteSolicitud solicitud);
    public void delete(int id);
    public List<SoporteSolicitud> buscarSoporteSolicitud(String titulo);
    public List<SoporteSolicitud> listarPorEstado(String estado);

    // Nuevo método para el reporte
    List<SoporteApartadoDTO> findSolicitudesPorApartado();
}
