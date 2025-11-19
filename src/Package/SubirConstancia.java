package Package;

import javax.swing.*;
import java.awt.*;

public class SubirConstancia extends JFrame {
    public JButton subirConstancia;
    String[] especialidades = {"Medicina", "Enfermeria", "Nutricion", "Odontología"};
    public JComboBox<String> especialidadesComboBox = new JComboBox<>(especialidades);
    public SubirConstancia() {
        setTitle("Subir Constancia");
        setSize(400, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        subirConstancia.setBounds(125, 100, 150, 30);
        centro.add(subirConstancia);

        especialidadesComboBox.setBounds(125, 50, 150, 30);
        centro.add(especialidadesComboBox);

        this.add(centro, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void inicializarComponentes(){
        subirConstancia = new JButton("Subir Constancia");
        especialidadesComboBox = new JComboBox<>(especialidades);
    }
}
