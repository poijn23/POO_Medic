package Package;

public class Guardia {
    private String tipoGuardia;
    private String horario;
    private String zonaTrabajo;

    public Guardia(String tipoGuardia, String horario, String zonaTrabajo) {
        this.tipoGuardia = tipoGuardia;
        this.horario = horario;
        this.zonaTrabajo = zonaTrabajo;
    }


    public String getTipoGuardia() {
        return tipoGuardia;
    }
    public void setTipoGuardia(String tipoGuardia) {
        this.tipoGuardia = tipoGuardia;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getZonaTrabajo() {
        return zonaTrabajo;
    }
    public void setZonaTrabajo(String zonaTrabajo) {
        this.zonaTrabajo = zonaTrabajo;
    }


}

