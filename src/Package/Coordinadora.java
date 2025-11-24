package Package;
import java.sql.Date;

public class Coordinadora extends Personal{
    private String cursos;

    public Coordinadora(String nombre, Date fechaNacimiento, String clavePersonal, String passPersonal,String rolId, String cursos) {
        super(nombre, fechaNacimiento, clavePersonal, passPersonal, rolId);
        this.cursos = cursos;
    }
    public String getCursos() {
        return this.cursos;

    }
    public void setCursos(String cursos) {
        this.cursos = cursos;
    }

    public void darAltaEstudiantes (){
    }
    public void validarConstancia(){

    }
    public void verreportesDeInasistencia(){

    }
    public void cargarCalificasiones(){
    }
    public void asignarGuardias(){

    }
    public void modificarGuardias(){
    }
    public void asignarTutores(){
    }
    public void modificarTutores(){}
    public void visualizarDocumentos(){}
    public void gestionarCursos(){}
    public void crearHistoricos(){
    }
    public void visualizarHistoricos(){}
    public void borrarDocumentos(){}

    }



