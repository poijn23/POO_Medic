package Package;
import java.util.Date;

public class Constancia {
    private int numSerie;
    private int idEstudiante;
    private String estado;
    private String observaciones;
    private String formato;
    private int tamanio;
    private Date fechaCurso;
    private Date fechaCarga;
    private Date fechaValidacion;
    private int idRevisor;
    private int idCurso;

    public Constancia(int numSerie, int idEstudiante, String estado, String observaciones, String formato, int tamanio, Date fechaCurso, Date fechaCarga, Date fechaValidacion, int idRevisor, int idCurso) {
        this.numSerie = numSerie;
        this.idEstudiante = idEstudiante;
        this.estado = estado;
        this.observaciones = observaciones;
        this.formato = formato;
        this.tamanio = tamanio;
        this.fechaCurso = fechaCurso;
        this.fechaCarga = fechaCarga;
        this.fechaValidacion = fechaValidacion;
        this.idRevisor = idRevisor;
        this.idCurso = idCurso;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public Date getFechaCurso() {
        return fechaCurso;
    }

    public void setFechaCurso(Date fechaCurso) {
        this.fechaCurso = fechaCurso;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public int getIdRevisor() {
        return idRevisor;
    }

    public void setIdRevisor(int idRevisor) {
        this.idRevisor = idRevisor;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void cambiarEstado(){};
}
