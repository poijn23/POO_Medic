package Package;

public class Calificacion {
    private String matricula;
    private String nombre;
    private String nombreCurso;
    private String calificacionCurso;

    public Calificacion(String matricula, String nombre, String nombreCurso, String calificacionCurso) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.nombreCurso = nombreCurso;
        this.calificacionCurso = calificacionCurso;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombreCurso() {
        return nombreCurso;
    }
    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
    public String getCalificacionCurso() {
        return calificacionCurso;
    }
    public void setCalificacionCurso(String calificacionCurso) {
        this.calificacionCurso = calificacionCurso;
    }

}
