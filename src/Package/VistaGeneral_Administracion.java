package Package;

import javax.swing.*;
import java.awt.*;
//hhhh
public class VistaGeneral_Administracion extends JFrame {
    public JButton altaCurso;
    public JButton crearEstudiante;
    public VistaGeneral_Administracion(){
        setTitle("VistaGeneral_Administración");
        setSize(350, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        altaCurso.setBounds(120, 30, 80, 25);
        centro.add(altaCurso);

        crearEstudiante.setBounds(20, 30, 80, 25);
        centro.add(crearEstudiante);

        this.add(centro, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void inicializarComponentes() {
        altaCurso = new JButton("Dar De Alta Un Curso");
        crearEstudiante = new JButton("Crear Estudiante");
    }
}
