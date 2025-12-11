package Package;
import java.time.LocalDate;

public class Curso {
    private int id;
    private String nombre;
    private boolean obligatorio;
    private boolean medicina;
    private boolean enfermeria;
    private boolean odontologia;
    private boolean nutriologia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Curso(){}

    public Curso(int id, String nombre, boolean obligatorio, boolean medicina, boolean enfermeria, boolean odontologia, boolean nutriologia, LocalDate fechaInicio, LocalDate fechaFin){
        this.id = id;
        this.nombre = nombre;
        this.obligatorio = obligatorio;
        this.medicina = medicina;
        this.enfermeria = enfermeria;
        this.odontologia = odontologia;
        this.nutriologia = nutriologia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    //Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreCurso() { return nombre; }
    public void setNombreCurso(String nombre) { this.nombre = nombre; }

    public boolean isObligatorio() { return obligatorio; }
    public void setObligatorio(boolean obligatorio) { this.obligatorio = obligatorio; }

    public boolean isMedicina() { return medicina; }
    public void setMedicina(boolean medicina) { this.medicina = medicina; }

    public boolean isEnfermeria() { return enfermeria; }
    public void setEnfermeria(boolean enfermeria) { this.enfermeria = enfermeria; }

    public boolean isOdontologia() { return odontologia; }
    public void setOdontologia(boolean odontologia) { this.odontologia = odontologia; }

    public boolean isNutriologia() { return nutriologia; }
    public void setNutriologia(boolean nutriologia) { this.nutriologia = nutriologia; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    @Override
    public String toString() {
        return nombre;
    }
}
