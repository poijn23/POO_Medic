package Package;
import java.sql.Date;
public class Personal {
    private String nombre;
    private Date fechaNacimiento;
    private String clavePersonal;
    private String passPersonal;
    private String rolId;
    public Personal(String nombre, Date fechaNacimiento, String clavePersonal, String passPersonal, String rolId) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.clavePersonal = clavePersonal;
        this.passPersonal = passPersonal;
        this.rolId = rolId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getClavePersonal() {
        return this.clavePersonal;
    }
    public void setClavePersonal(String clavePersonal) {
        this.clavePersonal = clavePersonal;
    }
    public String getPassPersonal() {
        return passPersonal;
    }
    public void setPassPersonal(String passPersonal) {
        this.passPersonal = passPersonal;
    }
    public String getRolId() {
        return rolId;
    }
    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    //Lo ocuparán coordinadora y secretaria
    public void darAltaEstudiantes (){

    }


}
