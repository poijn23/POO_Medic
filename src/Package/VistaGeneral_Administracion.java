package Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//hhhh
public class VistaGeneral_Administracion extends JFrame implements ActionListener{
    public JButton altaCurso;
    public JButton crearEstudiante;
    public JButton crearPersonal;
    public JButton subirCalificacion;
    public JButton generarReportes;
    public Consult_Database mydatabase = new Consult_Database();

    public VistaGeneral_Administracion(Consult_Database mydatabase) {
        setTitle("VistaGeneral_Administración");
        setSize(350, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        altaCurso.setBounds(20, 75, 150, 25);
        centro.add(altaCurso);

        crearEstudiante.setBounds(20, 30, 150, 25);
        centro.add(crearEstudiante);

        crearPersonal.setBounds(20, 100, 150, 25);
        centro.add(crearPersonal);

        subirCalificacion.setBounds(20, 120, 150, 25);
        centro.add(subirCalificacion);

        generarReportes.setBounds(20, 150, 150, 25);
        centro.add(generarReportes);

        this.add(centro, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setbuttoms();
        setVisible(true);
        this.mydatabase=mydatabase;
    }

    public void inicializarComponentes() {
        altaCurso = new JButton("Cursos");
        crearEstudiante = new JButton("Crear Estudiante");
        crearPersonal = new JButton("Crear Personal");
        subirCalificacion = new JButton("Subir Calificacion");
        generarReportes = new JButton("Generar Reportes");
    }

    private void setbuttoms() {
        altaCurso.addActionListener(this);
        altaCurso.setActionCommand("altaCurso");
        crearEstudiante.addActionListener(this);
        crearEstudiante.setActionCommand("crearEstudiante");
        crearPersonal.addActionListener(this);
        crearPersonal.setActionCommand("crearPersonal");
        subirCalificacion.addActionListener(this);
        subirCalificacion.setActionCommand("subirCalificacion");
        generarReportes.addActionListener(this);
        generarReportes.setActionCommand("generarReportes");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "altaCurso":{
                VistaGeneral_Curso vista = new VistaGeneral_Curso(mydatabase);
                break;
            }
            case "crearEstudiante":{
                crearEstudiante newform = new crearEstudiante(mydatabase);
                break;
            }
            case "subirCalificacion":{
                SubirCaliInterfaz subirCali = new SubirCaliInterfaz(mydatabase);
                break;
            }
            case  "generarReportes":{
                GenerarReporte generarReporte = new GenerarReporte(mydatabase);
                break;
            }
            case "crearPersonal":{
                RegistroPersonal registroPersonal = new RegistroPersonal(mydatabase);
                break;
            }
            default:{
                break;
            }
        }
    }
}
