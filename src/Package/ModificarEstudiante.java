package Package;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModificarEstudiante extends JFrame{
    private JPanel panelContenido;
    private JTextField campoNombre;
    private JTextField campoMatricula;
    private JTextField campoContrasena;
    private JTextField campoGuardia;
    private JTextField campoINE;
    private JTextField campoCURP;
    private JButton cancelarButton;
    private JButton modificarButton;
    private JComboBox campoTipoEst;
    private JLabel cargandoMod;
    private JCheckBox medicinaCheckBox;
    private JCheckBox odontologíaCheckBox;
    private JCheckBox enfermeríaCheckBox;
    private JCheckBox nutriciónCheckBox;

    String nombre = campoNombre.getText();
    String matricula = campoMatricula.getText();
    String contrasena = campoContrasena.getText();
    String guardia = campoGuardia.getText();
    String ine = campoINE.getText();
    String curp = campoCURP.getText();

    // Campo Especialidad como .json para

    public ModificarEstudiante() {
        modificarButton.addMouseListener(new MouseAdapter() {
            // Al hacer clic al botón de modificar
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cargandoMod != null){
                    cargandoMod.setVisible(true);
                }
                modificarButton.setEnabled(false);


                if (nombre.isEmpty()){
                    JOptionPane.showMessageDialog(panelContenido, "Datos incompletos, " +
                            "revise de nuevo su entrada");
                }
            }
        });
    }
}
