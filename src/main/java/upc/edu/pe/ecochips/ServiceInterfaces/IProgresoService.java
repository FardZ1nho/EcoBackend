package upc.edu.pe.ecochips.ServiceInterfaces;

import upc.edu.pe.ecochips.Entities.Progreso;
import java.util.List;

public interface IProgresoService {
    List<Progreso> list();
    void insert(Progreso progreso);
    Progreso listId(int id);  // Cambiado de Integer a int
    void update(Progreso progreso);
    void delete(int id);      // Cambiado de Integer a int

    // Métodos específicos para progreso
    Progreso obtenerProgresoPorUsuario(Integer idUsuario);
    void agregarPuntos(Integer idUsuario, Integer puntos);
    void cambiarEstado(Integer idUsuario, String estado);
}