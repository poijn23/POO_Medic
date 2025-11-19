package Package;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;

public class ModificarEstudiante extends JFrame{
    private JPanel panelContenido;
    private JTextField campoNombre;
    private JTextField campoMatricula;
    private JTextField campoContrasena;
    private JTextField campoGuardia;
    private JTextField campoArea;
    private JTextField campoINE;
    private JTextField campoCURP;
    private JButton cancelarButton;
    private JButton modificarButton;
    private JComboBox campoTipoEst;

    String nombre = campoNombre.getText();
    String matricula = campoMatricula.getText();
    String contrasena = campoContrasena.getText();
    String guardia = campoGuardia.getText();
    String area = campoArea.getText();
    String ine = campoINE.getText();
    String curp = campoCURP.getText();


    public ModificarEstudiante() {
        modificarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Se hace clic al botón de modificar
                if (nombre.isEmpty()){

                }
            }
        });
    }
}
