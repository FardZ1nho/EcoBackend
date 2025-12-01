package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.Entities.Alimento;
import upc.edu.pe.ecochips.Entities.RegistroAlimentacion;
import upc.edu.pe.ecochips.Repositories.IAlimentoRepository;
import upc.edu.pe.ecochips.Repositories.IRegistroAlimentacionRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRegistroAlimentacionService;

import java.util.List;

@Service
public class RegistroAlimentacionServiceImplements implements IRegistroAlimentacionService {

    @Autowired
    private IRegistroAlimentacionRepository rR;

    @Autowired
    private IAlimentoRepository alimentoRepository;

    @Override
    public void insert(RegistroAlimentacion registroAlimentacion) {
        // Buscar el alimento para obtener el co2Porcion
        Alimento alimento = alimentoRepository.findById(registroAlimentacion.getAlimento().getIdAlimento()).orElse(null);
        if (alimento != null) {
            // Calcular CO2 emitido: porciones * co2Porcion
            Double co2Emitido = registroAlimentacion.getPorciones() * alimento.getCo2Porcion();
            registroAlimentacion.setCo2Emitido(co2Emitido);
        }

        // Guardar el registro
        rR.save(registroAlimentacion);
    }

    @Override
    public List<RegistroAlimentacion> list() {
        return rR.findAll();
    }

    @Override
    public RegistroAlimentacion listId(int id) {
        return rR.findById(id).orElse(null);
    }

    @Override
    public void update(RegistroAlimentacion registroAlimentacion) {
        // Buscar el alimento para recalcular CO2
        Alimento alimento = alimentoRepository.findById(registroAlimentacion.getAlimento().getIdAlimento()).orElse(null);
        if (alimento != null) {
            // Recalcular CO2 emitido: porciones * co2Porcion
            Double co2Emitido = registroAlimentacion.getPorciones() * alimento.getCo2Porcion();
            registroAlimentacion.setCo2Emitido(co2Emitido);
        }

        // Guardar el registro actualizado
        rR.save(registroAlimentacion);
    }

    @Override
    public void delete(int id) {
        rR.deleteById(id);
    }

    @Override
    public List<RegistroAlimentacion> listarPorUsuario(Integer idUsuario) {
        return rR.findByUsuarioIdUsuario(idUsuario);
    }
}