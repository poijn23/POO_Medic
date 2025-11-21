package Package;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
            System.out.println("||||||||||||||");
            Time time= Time.valueOf(LocalTime.now());
            Date date= Date.valueOf(LocalDate.now());
            if(!myDatabase.isInAsistencia(Matricula.getText(), date)){
                myDatabase.createTuplaAsistencia(Matricula.getText(),date,time);
            }else{
                myDatabase.registroSalida(Matricula.getText(),date, time);
            }
        }
    }
}

