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
    public String getArea() {return area;}
    public String getGuardia() {return guardia;}
    public String getTipoEstudiante() {return tipoEstudiante;}
    public String getINE() {return INE;}
    public String getCURP() {return CURP;}
    public String getActaNacimiento() {return actaNacimiento;}
    public String getHojaPresentacion() {return hojaPresentacion;}

    // Setters
    public void setNombreEst(String nombreEst) {this.nombreEst = nombreEst;}
    public void setMatricula(int matricula) {this.matricula = matricula;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}
    public void setArea(String area) {this.area = area;}
    public void setGuardia(String guardia) {this.guardia = guardia;}
    public void setTipoEstudiante(String tipoEstudiante) {this.tipoEstudiante = tipoEstudiante;}
    public void setINE(String INE) {this.INE = INE;}
    public void setCURP(String CURP) {this.CURP = CURP;}
    public void setActaNacimiento(String actaNacimiento) {this.actaNacimiento = actaNacimiento;}
    public void setHojaPresentacion(String hojaPresentacion) {this.hojaPresentacion = hojaPresentacion;}


}


