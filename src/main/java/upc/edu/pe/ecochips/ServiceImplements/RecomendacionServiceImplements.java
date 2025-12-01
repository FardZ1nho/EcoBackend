package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.RecomendacionTipoDTO;
import upc.edu.pe.ecochips.Entities.Recomendacion;
import upc.edu.pe.ecochips.Repositories.IRecomendacionRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRecomendacionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacionServiceImplements implements IRecomendacionService {

    @Autowired
    private IRecomendacionRepository rR;

    @Override
    public List<Recomendacion> list() {
        return rR.findAll();
    }

    @Override
    public void insert(Recomendacion recomendacion) {
        rR.save(recomendacion);
    }

    @Override
    public Recomendacion listId(int id) {
        return rR.findById(id).orElse(null);
    }

    @Override
    public void update(Recomendacion recomendacion) {
        rR.save(recomendacion);
    }

    @Override
    public void delete(int id) {
        rR.deleteById(id);
    }

    // En RecomendacionServiceImplement.java
    @Override
    public List<RecomendacionTipoDTO> findRecomendacionesPorTipo() {
        List<Object[]> results = rR.findRecomendacionesPorTipo();
        return results.stream()
                .map(result -> new RecomendacionTipoDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}