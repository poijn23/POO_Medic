package Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaGeneral_Estudiantes extends JFrame implements ActionListener {
    private Consult_Database myDatabase;
    public JButton subirConstancia;


    public VistaGeneral_Estudiantes(Consult_Database myDatabase) {
        setTitle("VistaGeneral_Estudiantes");
        setSize(350, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        subirConstancia.setBounds(120, 30, 80, 25);
        centro.add(subirConstancia);

        this.add(centro);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setbuttoms();
        this.myDatabase=myDatabase;
    }

    public void inicializarComponentes(){
        subirConstancia = new JButton("Subir Constancia");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Subir Constancia": {
                this.dispose();
                SubirConstancia constanciaVista = new SubirConstancia(myDatabase);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void setbuttoms(){
        subirConstancia.addActionListener(this);
        subirConstancia.setActionCommand("Subir Constancia");
    }
}


