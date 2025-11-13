package Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroAsistencia implements ActionListener {
    public JPanel panel1;
    private JTextField Matricula;
    private JPasswordField password;
    public JButton confirmarButton;

    public RegistroAsistencia() {
        confirmarButton.addActionListener(this);
        confirmarButton.setActionCommand("Confirmar");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Confirmar")) {
            System.out.println(this.Matricula.getText());
            System.out.println(new String(password.getPassword()));
        }
    }
}

