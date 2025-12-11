package Package;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarEstudiante extends JFrame {
    private JPanel panelContenido;
    private JTextField campoNombre;
    private JTextField campoMatricula;
    private JTextField campoContrasena;
    private JTextField campoFecha;
    private JButton cancelarButton;
    private JButton modificarButton;
    private JComboBox campoArea;
    private JComboBox campoEspecialidad;
    private JTextField campoCURP;
    private JLabel cargandoLabel;

    Consult_Database mysql;

    // Campo Especialidad como .json para (?)

    // Validación de Parámetros
    public boolean validarDatos(String nombre, String matricula, String contrasena, String curp){
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (matricula == null || matricula.trim().isEmpty()) return false;
        if (contrasena == null || contrasena.trim().isEmpty()) return false;
        if (curp == null || curp.trim().isEmpty()) return false;
        if (curp.length() < 18) return false;

        return true;
    }

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
                    String fechaNacimiento = campoFecha.getText().trim();
                    String curp = campoCURP.getText();

                    String especialidad = (String) campoEspecialidad.getSelectedItem();
                    String tipoEstudiante = "";


                    if (especialidad.equals("Medicina")){
                        tipoEstudiante = (String) campoArea.getSelectedItem();
                    }else{
                        // No medicina, deben de ser servicio social
                        tipoEstudiante = "Servicio Social";
                    }

                    // Validación
                    if (!validarDatos(nombre, matricula, contrasena, curp)){
                        JOptionPane.showMessageDialog(panelContenido, "Datos incompletos o CURP inválida", "ERROR", JOptionPane.ERROR_MESSAGE);
                        if (cargandoLabel != null) cargandoLabel.setVisible(false);
                        modificarButton.setEnabled(true);
                        return;
                    }

                    final String tipoFinal = tipoEstudiante;

                    try {
                        //Se necesita un procedimiento para hacer update a una tupla
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panelContenido, "Error al guardar en BD o fecha inválida: " + ex.getMessage());
                        modificarButton.setEnabled(true);
                        if (cargandoLabel != null) cargandoLabel.setVisible(false);
                        return;
                    }


                }
            });

        }

    }

}


