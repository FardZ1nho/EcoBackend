package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.DTOs.SoporteRespuestaListDTO;
import upc.edu.pe.ecochips.Entities.SoporteRespuesta;

import java.util.List;

public interface ISoporteRespuestaService {
    public List<SoporteRespuesta> listarRespuestas();
    public SoporteRespuesta obtenerRespuestaPorId(int id);
    public SoporteRespuesta guardarRespuesta(SoporteRespuesta respuesta);
    public void delete(int id);
    public void update(SoporteRespuesta SoporteRespuesta);
    public void insert(SoporteRespuesta SoporteRespuesta);
    List<SoporteRespuestaListDTO> listarRespuestasReducidas();
}