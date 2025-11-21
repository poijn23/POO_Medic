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
                    SubirConstancia subirConstancia = new SubirConstancia();
                }
                break;
            }
        }
    }

    private void setButtoms(){
        asistenciaButton.addActionListener(this);
        asistenciaButton.setActionCommand("asistencia");
        iniciarButton.addActionListener(this);
        iniciarButton.setActionCommand("iniciar");
    }

}
