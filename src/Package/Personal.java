package Package;
import java.sql.Date;
public class Personal {
    private String nombre;
    private Date fechanacimiento;
    private String clavePersonal;
    private String passPersonal;
    public Personal(String nombre, Date fechanacimiento, String clavePersonal, String passPersonal) {
        this.nombre = nombre;
        this.fechanacimiento = fechanacimiento;
        this.clavePersonal = clavePersonal;
        this.passPersonal = passPersonal;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getFechanacimiento() {
        return this.fechanacimiento;
    }
    public void setFechanacimiento(Date fechanacimiento) {}
    public String getClavePersonal() {
        return this.clavePersonal;
    }
    public void setClavePersonal(String clavePersonal) {}
    public String getPassPersonal() {
        return passPersonal;
    }
    public void setPassPersonal(String passPersonal) {}
}
