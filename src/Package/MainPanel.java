package Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame implements ActionListener {
    private final Consult_Database myDatabase = new Consult_Database();
    private JButton asistenciaButton;
    private JPanel HOLDER;
    private JPanel defaulPanel;
    private JPanel cocntentpane;
    private JTextField user;
    private JPasswordField password;
    private JButton iniciarButton;
    private JButton altaEstudiante;

    public MainPanel()
    {

        this.setContentPane( defaulPanel );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        setButtoms();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "asistencia":{
                this.dispose();
                RegistroAsistencia asistencia = new RegistroAsistencia(myDatabase);
                break;
            }
            case "iniciar":{
                if(myDatabase.validarUsuario(user.getText(), new String(password.getPassword()))) {
                    this.dispose();
                    switch (myDatabase.getRol(user.getText(), new String(password.getPassword()))) {
                        case "personal":{
                            this.dispose();
                            SubirConstancia subirConstancia = new SubirConstancia();
                            break;
                        }
                        case "Alum":{
                            break;
                        }
                        default:{
                            break;
                        }
                    }
                }
                break;
            }
            case "altaEstudiante":{
                System.out.println("Hola");
                crearEstudiante crearEstudiante = new crearEstudiante(myDatabase);
                break;
            }
        }
    }

    private void setButtoms(){
        asistenciaButton.addActionListener(this);
        asistenciaButton.setActionCommand("asistencia");
        iniciarButton.addActionListener(this);
        iniciarButton.setActionCommand("iniciar");
        altaEstudiante.addActionListener(this);
        altaEstudiante.setActionCommand("altaEstudiante");
    }

}
