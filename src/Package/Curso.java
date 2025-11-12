package Package;
public class Curso {
    protected int idCurso;
    protected String tipoCurso;
    protected String nombreCurso;

    public Curso(int idCurso, String tipoCurso, String nombreCurso) {
        this.idCurso = idCurso;
        this.tipoCurso = tipoCurso;
        this.nombreCurso = nombreCurso;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}
