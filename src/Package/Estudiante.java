package Package;

public class Estudiante {
    protected String nombreEst;
    protected int matricula;
    protected  String contrasena;
    protected String area;
    protected String guardia;
    protected String tipoEstudiante;
    protected String INE;
    protected String CURP;
    protected String actaNacimiento;    // Acta debe ser archivo??
    protected String hojaPresentacion;  // Hoja de presentación debería ser archivo??

    public Estudiante(String nombreEst, int matricula, String contrasena, String area,
                      String guardia, String tipoEstudiante, String INE, String CURP,
                      String actaNacimiento, String hojaPresentacion){
        this.nombreEst = nombreEst;
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.area = area;
        this.guardia = guardia;
        this.tipoEstudiante = tipoEstudiante;
        this.INE = INE;
        this.CURP = CURP;
        this.actaNacimiento = actaNacimiento;
        this.hojaPresentacion = hojaPresentacion;
    }
    // Getters


    public String getNombreEst() {return nombreEst;}
    public int getMatricula() {return matricula;}
    public String getContrasena() {return contrasena;}
}


