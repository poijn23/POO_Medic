package Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroAsistencia extends JFrame implements ActionListener {
    Consult_Database myDatabase;
    public JPanel panel1;
    private JTextField Matricula;
    private JPasswordField password;
    public JButton confirmarButton;

    public RegistroAsistencia(Consult_Database con) {
        myDatabase = con;
        confirmarButton.addActionListener(this);
        confirmarButton.setActionCommand("Confirmar");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocationRelativeTo(null);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Confirmar")) {
            System.out.println(this.Matricula.getText());
            System.out.println(new String(password.getPassword()));
        }
    }
}

