package Package;

public class Calificacion {
    private String matricula;
    private String nombre;
    private String nombrePeriodo;
    private float calificacionFinal;

    public Calificacion(String matricula, String nombre, String nombrePeriodo, float calificacionFinal) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.nombrePeriodo = nombrePeriodo;
        this.calificacionFinal = calificacionFinal;
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
    public String getNombrePeriodo() {
        return nombrePeriodo;
    }
    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }
    public float getCalificacionFinal() {
        return calificacionFinal;
    }
    public void setCalificacionFinal(float calificacionFinal) {
        this.calificacionFinal = calificacionFinal;
    }
}
