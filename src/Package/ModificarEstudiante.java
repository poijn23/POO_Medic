package Package;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarEstudiante extends JFrame {
    private JPanel panelContenido;
    private JTextField campoNombre;
    private JTextField campoMatricula;
    private JTextField campoContrasena;
    private JTextField campoGuardia;
    private JButton cancelarButton;
    private JButton modificarButton;
    private JComboBox campoArea;
    private JComboBox campoEspecialidad;
    private JTextField campoCURP;
    private JLabel cargandoLabel;

    Consult_Database mysql;

    // Campo Especialidad como .json para

    // - Constructor -
    public ModificarEstudiante(Consult_Database database) {
        mysql = database;

        // Boton Modificar
        if (modificarButton != null) {

            modificarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cargandoLabel != null) cargandoLabel.setVisible(false);
                    modificarButton.setEnabled(false);

                    String nombre = campoNombre.getText();
                    String matricula = campoMatricula.getText();
                    String contrasena = campoContrasena.getText();

                }
            });

        }

    }

}


