package Package;

import Package.RegistroAsistencia;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroAsistencia_metodos extends JFrame implements ActionListener {
    private RegistroAsistencia registroAsistenciaPanel;

    public RegistroAsistencia_metodos(RegistroAsistencia registroAsistencia) {
        this.registroAsistenciaPanel = registroAsistencia;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Confirmar")) {
            String usuario = registroAsistenciaPanel.getMatricula().getText();
            String pass = new String(registroAsistenciaPanel.getPassword().getPassword());
            System.out.println(pass);
            System.out.println(usuario);
        }
    }
}

