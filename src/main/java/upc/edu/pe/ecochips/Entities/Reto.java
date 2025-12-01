package upc.edu.pe.ecochips.Entities;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Reto")
public class Reto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reto")
    private int idReto;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "objetivo_kg")
    private double objetivoKg;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    // En la entidad Reto, agregar:
    @Column(name = "canjes_recompensa")
    private Integer canjesRecompensa = 1;

    //relacion inversa con participacionReto
    @OneToMany(mappedBy = "reto", cascade = CascadeType.ALL)
    private List<ParticipacionReto> participaciones;

    public Reto() {}

    public Reto(int idReto, List<ParticipacionReto> participaciones, Integer canjesRecompensa, LocalDate fechaInicio, LocalDate fechaFin, double objetivoKg, String descripcion, String titulo) {
        this.idReto = idReto;
        this.participaciones = participaciones;
        this.canjesRecompensa = canjesRecompensa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.objetivoKg = objetivoKg;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public int getIdReto() {
        return idReto;
    }

    public Integer getCanjesRecompensa() {
        return canjesRecompensa;
    }

    public void setCanjesRecompensa(Integer canjesRecompensa) {
        this.canjesRecompensa = canjesRecompensa;
    }

    public void setIdReto(int idReto) {
        this.idReto = idReto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getObjetivoKg() {
        return objetivoKg;
    }

    public void setObjetivoKg(double objetivoKg) {
        this.objetivoKg = objetivoKg;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<ParticipacionReto> getParticipaciones() {
        return participaciones;
    }

    public void setParticipaciones(List<ParticipacionReto> participaciones) {
        this.participaciones = participaciones;
    }
}
