package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.SoporteApartadoDTO;
import upc.edu.pe.ecochips.Entities.SoporteSolicitud;
import upc.edu.pe.ecochips.Repositories.ISoporteSolicitudRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.ISoporteSolicitudService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoporteSolicitudImplements  implements ISoporteSolicitudService {
    @Autowired
    private ISoporteSolicitudRepository sR;

    @Override
    public List<SoporteSolicitud> list() {
        return sR.findAll();
    }

    @Override
    public void insert(SoporteSolicitud solicitud) {
        sR.save(solicitud);
    }

    @Override
    public SoporteSolicitud listID(int id) {
        return sR.findById(id).orElse(null);
    }

    @Override
    public void update(SoporteSolicitud solicitud) {
        sR.save(solicitud);
    }

    @Override
    public void delete(int id) {
        sR.deleteById(id);
    }

    @Override
    public List<SoporteSolicitud> buscarSoporteSolicitud(String titulo) {
        return sR.buscarPorTitulo(titulo);
    }

    @Override
    public List<SoporteSolicitud> listarPorEstado(String estado) {return sR.listarPorEstado(estado);}

    // En SoporteSolicitudServiceImplement.java
    @Override
    public List<SoporteApartadoDTO> findSolicitudesPorApartado() {
        List<Object[]> results = sR.findSolicitudesPorApartado();
        return results.stream()
                .map(result -> new SoporteApartadoDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}
