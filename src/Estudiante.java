public class Estudiante {
    private String nombre;
    private String matricula;
    private String contraseña;
    private String guardia;
    private String area;
    private String tipoEstudiante;
    private String ine;
    private String curp;
    private String actaNacimiento;
    private String hojaPresentacion;

    public Estudiante(String nombre, String matricula, String contraseña, String guardia, String area, String tipoEstudiante, String ine, String curp, String actaNacimiento, String hojaPresentacion) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.contraseña = contraseña;
        this.guardia = guardia;
        this.area = area;
        this.tipoEstudiante = tipoEstudiante;
        this.ine = ine;
        this.curp = curp;
        this.actaNacimiento = actaNacimiento;
        this.hojaPresentacion = hojaPresentacion;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getGuardia() {
        return guardia;
    }

    public void setGuardia(String guardia) {
        this.guardia = guardia;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getTipoEstudiante() {
        return tipoEstudiante;
    }
    public void setTipoEstudiante(String tipoEstudiante) {
        this.tipoEstudiante = tipoEstudiante;
    }
    public String getIne() {
        return ine;
    }
    public void setIne(String ine) {
        this.ine = ine;
    }
    public String getCurp() {
        return curp;
    }
    public void setCurp(String curp) {
        this.curp = curp;
    }
    public String getActaNacimiento() {
        return actaNacimiento;
    }
    public void setActaNacimiento(String actaNacimiento) {
        this.actaNacimiento = actaNacimiento;
    }
    public String getHojaPresentacion() {
        return hojaPresentacion;
    }
    public void setHojaPresentacion(String hojaPresentacion) {
        this.hojaPresentacion = hojaPresentacion;
    }
}
