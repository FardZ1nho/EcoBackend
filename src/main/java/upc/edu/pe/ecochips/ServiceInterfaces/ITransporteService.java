package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.TransporteImpactoDTO;
import upc.edu.pe.ecochips.Entities.Transporte;

import java.util.List;

public interface ITransporteService {
    public List<Transporte> list();
    public void insert(Transporte d);
    public Transporte listId(int id);
    public void update(Transporte d);
    public void delete(int id);


    // Nuevo método para el reporte
    List<TransporteImpactoDTO> findTop5TransportesMasContaminantes();
}
