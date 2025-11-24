package Package;
import java.sql.Date;
public class Secretario extends Personal{

    public Secretario(String nombre, Date fechaNacimiento, String clavePersonal,
                      String passPersonal, String rolId) {
        super(nombre, fechaNacimiento, clavePersonal, passPersonal, rolId);
    }
    public void asignarGuardias(){

    }
    public void modificarGuardias(){
    }
    public void asignarTutores(){
    }
    public void generarReporte(){
    }
    public void visualizarDocumentos(){}
    public void gestionarCursos(){}
}
