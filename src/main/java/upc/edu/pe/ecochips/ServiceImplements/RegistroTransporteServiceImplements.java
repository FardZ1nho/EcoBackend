package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.RegistroTransporte;
import upc.edu.pe.ecochips.Entities.Transporte;
import upc.edu.pe.ecochips.Repositories.IRegistroTransporteRepository;
import upc.edu.pe.ecochips.Repositories.ITransporteRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRegistroTransporteService;
import upc.edu.pe.ecochips.ServiceInterfaces.IProgresoService;

import java.util.List;

@Service
public class RegistroTransporteServiceImplements implements IRegistroTransporteService {

    @Autowired
    private IRegistroTransporteRepository rtR;

    @Autowired
    private ITransporteRepository tR;

    @Autowired
    private IProgresoService pS;

    @Override
    public void insert(RegistroTransporte registroTransporte) {
        // Buscar el transporte para obtener el factor CO2
        Transporte transporte = tR.findById(registroTransporte.getTransporte().getIdTransporte()).orElse(null);
        if (transporte != null) {
            // Calcular CO2 emitido: distancia_km * factor_co2
            Double co2Emitido = registroTransporte.getDistanciaKm() * transporte.getFactorCo2();
            registroTransporte.setCo2Emitido(co2Emitido);
        }

        // Guardar el registro
        rtR.save(registroTransporte);

        // Agregar 1 punto de progreso al usuario
        pS.agregarPuntos(registroTransporte.getUsuario().getIdUsuario(), 1);
    }

    @Override
    public List<RegistroTransporte> list() {
        return rtR.findAll();
    }

    @Override
    public RegistroTransporte listId(int id) {
        return rtR.findById(id).orElse(null);
    }

    @Override
    public void update(RegistroTransporte registroTransporte) {
        // Buscar el transporte para recalcular CO2
        Transporte transporte = tR.findById(registroTransporte.getTransporte().getIdTransporte()).orElse(null);
        if (transporte != null) {
            // Recalcular CO2 emitido: distancia_km * factor_co2
            Double co2Emitido = registroTransporte.getDistanciaKm() * transporte.getFactorCo2();
            registroTransporte.setCo2Emitido(co2Emitido);
        }

        // Guardar el registro actualizado
        rtR.save(registroTransporte);
    }

    @Override
    public void delete(int id) {
        rtR.deleteById(id);
    }

    @Override
    public List<RegistroTransporte> listarPorUsuario(Integer idUsuario) {
        return rtR.findByUsuarioIdUsuario(idUsuario);
    }
}