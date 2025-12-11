package Package;

import java.sql.Date;

public class Reporte {

    private String tipoReporte;
    private String descripcion;
    private String contenido;
    private Date fechaElaboracion;
    private String clavePersonal;

    public Reporte(String tipoReporte, String descripcion, String contenido, Date fechaElaboracion, String clavePersonal) {
        this.tipoReporte = tipoReporte;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.fechaElaboracion = fechaElaboracion;
        this.clavePersonal = clavePersonal;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getClavePersonal() {
        return clavePersonal;
    }

    public void setClavePersonal(String clavePersonal) {
        this.clavePersonal = clavePersonal;
    }
}