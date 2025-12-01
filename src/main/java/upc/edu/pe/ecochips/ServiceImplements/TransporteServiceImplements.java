package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.TransporteImpactoDTO;
import upc.edu.pe.ecochips.Entities.Transporte;
import upc.edu.pe.ecochips.Repositories.ITransporteRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.ITransporteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransporteServiceImplements implements ITransporteService {

    @Autowired
    private ITransporteRepository tR;

    @Override
    public List<Transporte> list() {
        return tR.findAll();
    }

    @Override
    public void insert(Transporte t) {
        tR.save(t);
    }

    @Override
    public Transporte listId(int id) {
        return tR.findById(id).orElse(null);
    }

    @Override
    public void update(Transporte t) {
        tR.save(t);
    }

    @Override
    public void delete(int id) {
        tR.deleteById(id);
    }

    // En TransporteServiceImplement.java
    @Override
    public List<TransporteImpactoDTO> findTop5TransportesMasContaminantes() {
        List<Object[]> results = tR.findTop5TransportesMasContaminantes();
        return results.stream()
                .map(result -> new TransporteImpactoDTO(
                        (String) result[0],
                        ((Number) result[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }
}