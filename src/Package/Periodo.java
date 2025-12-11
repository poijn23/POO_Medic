package Package;

import java.util.Date;

public class Periodo {
    private int idPeriodo;
    private String nombrePeriodo;
    private Date  fechaInicio;
    private Date fechaFin;

    public Periodo (int idPeriodo, String nombrePeriodo){
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = new Date();
        this.fechaFin = new Date();
    }
    public int getIdPeriodo() {
        return idPeriodo;
    }
    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
    public String getNombrePeriodo() {
        return nombrePeriodo;
    }
    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }
    public Date getFechaInicio() {return fechaInicio;}
    public void setFechaInicio(Date fechaInicio) {this.fechaInicio = fechaInicio;}


}
