package Package;

import java.sql.Date;
public class Tecnico extends Personal {
    public Tecnico(String nombre,Date fechaNacimiento, String clavePersonal, String passPersonal, String rolID){
    super(nombre,fechaNacimiento,clavePersonal,passPersonal,rolID);
    }
    public void cambiarContraseña(String rolID){} // usr personal
    public void cambiarContraseña(){} // usr Estudiante
}
