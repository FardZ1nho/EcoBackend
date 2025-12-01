package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.RecompensaCostoDTO;
import upc.edu.pe.ecochips.Entities.Recompensa;
import upc.edu.pe.ecochips.Repositories.IRecompensaRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IRecompensaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensaServiceImplements implements IRecompensaService {

    @Autowired
    private IRecompensaRepository rR;

    @Override
    public List<Recompensa> list() {
        return rR.findAll();
    }

    @Override
    public void insert(Recompensa recompensa) {
        rR.save(recompensa);
    }

    @Override
    public Recompensa listId(int id) {
        return rR.findById(id).orElse(null);
    }

    @Override
    public void update(Recompensa recompensa) {
        rR.save(recompensa);
    }

    @Override
    public void delete(int id) {
        rR.deleteById(id);
    }

    @Override
    public List<Recompensa> buscarPorCostoCanjes(int costoCanjes) {
        return rR.findByCostoCanjes(costoCanjes);
    }

    @Override
    public List<Recompensa> buscarPorTitulo(String titulo) {
        return rR.findByTituloContaining(titulo);
    }

    @Override
    public List<RecompensaCostoDTO> findAllRecompensasMasCostosas() {
        List<Object[]> results = rR.findAllRecompensasMasCostosas();
        return results.stream()
                .map(result -> new RecompensaCostoDTO(
                        (String) result[0],
                        (Integer) result[1]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecompensaCostoDTO> findAllRecompensasMasBaratas() {
        List<Object[]> results = rR.findAllRecompensasMasBaratas();
        return results.stream()
                .map(result -> new RecompensaCostoDTO(
                        (String) result[0],
                        (Integer) result[1]
                ))
                .collect(Collectors.toList());
    }
}