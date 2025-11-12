package Package;
import java.sql.Date;

public class Coordinadora extends Personal{
    private String cursos;

    public Coordinadora(String nombre, Date fechaNacimiento, String clavePersonal, String passPersonal,String rolId, String cursos) {
        super(nombre, fechaNacimiento, clavePersonal, passPersonal, rolId);
        this.cursos = cursos;
    }
    public String getCursos() {
        return cursos;
    }
    public void setCursos(String cursos) {
        this.cursos = cursos;
    }
}
