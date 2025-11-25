package Package;

import javax.swing.*;

public class VistaGeneral_Estudiantes extends JFrame {
    public JButton subirConstancia;
    public VistaGeneral_Estudiantes() {
        setTitle("VistaGeneral_Administración");
        setSize(350, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        subirConstancia.setBounds(120, 30, 80, 25);
        centro.add(subirConstancia);

        this.add(centro);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void inicializarComponentes(){
        subirConstancia = new JButton("Subir Constancia");
    }

}
