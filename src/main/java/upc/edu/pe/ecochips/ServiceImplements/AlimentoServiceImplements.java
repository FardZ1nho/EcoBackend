package upc.edu.pe.ecochips.ServiceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.ecochips.DTOs.TipoAlimentoCO2DTO;
import upc.edu.pe.ecochips.Entities.Alimento;
import upc.edu.pe.ecochips.Repositories.IAlimentoRepository;
import upc.edu.pe.ecochips.ServiceInterfaces.IAlimentoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlimentoServiceImplements implements IAlimentoService {

    @Autowired
    private IAlimentoRepository aR;

    @Override
    public List<Alimento> list() {
        return aR.findAll();
    }

    @Override
    public void insert(Alimento alimento) {
        aR.save(alimento);
    }

    @Override
    public Alimento listId(int id) {
        return aR.findById(id).orElse(null);
    }

    @Override
    public void update(Alimento alimento) {
        aR.save(alimento);
    }

    @Override
    public void delete(int id) {
        aR.deleteById(id);
    }

    @Override
    public List<TipoAlimentoCO2DTO> findPromedioCO2PorTipoAlimento() {
        List<Object[]> results = aR.findPromedioCO2PorTipoAlimento();
        return results.stream()
                .map(result -> new TipoAlimentoCO2DTO(
                        (String) result[0],
                        ((Number) result[1]).doubleValue(),
                        ((Number) result[2]).longValue()
                ))
                .collect(Collectors.toList());
    }
}