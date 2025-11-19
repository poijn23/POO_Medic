package Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class crearEstudiante {

    // --- VARIABLES ---
    private JPanel panelPrincipal;
    private JPanel panelDatosPersonales;
    private JPanel panelDatosAcademicos;

    // Tus campos (asegúrate que los nombres coincidan)
    private JTextField nombreTextField;
    private JTextField matriculaTextField;
    private JPasswordField contrasenapasswordField;
    private JTextField fechadeNacimientotextField;
    private JTextField curptextField;
    private JComboBox tipodeEstudiantecomboBox;

    private JPanel panelResidente;
    private JTextField areatextField;

  //boton
    private JButton guardarbutton; // El botón de guardar
    private JLabel cargandoLabel; // El texto de "Cargando..."

    // --- CONSTRUCTOR ---
    public crearEstudiante() {
        tipodeEstudiantecomboBox.addItem("Regular");
        tipodeEstudiantecomboBox.addItem("Residente");

        if (panelResidente != null) {
            panelResidente.setVisible(false);
        }

        // Para hacerlo invisible
        if (cargandoLabel != null) {
            cargandoLabel.setVisible(false);
        }


        tipodeEstudiantecomboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String seleccion = (String) tipodeEstudiantecomboBox.getSelectedItem();
                    if (panelResidente != null) {
                        if (seleccion.equals("Residente")) {
                            panelResidente.setVisible(true);
                        } else {
                            panelResidente.setVisible(false);
                            if (areatextField != null) {
                                areatextField.setText("");
                            }
                        }
                    }
                    if (panelPrincipal != null) {
                        SwingUtilities.getWindowAncestor(panelPrincipal).pack();
                    }
                }
            }
        });

        //BOTÓN GUARDAR
        if (guardarbutton != null) {
            guardarbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cargandoLabel != null) {
                        cargandoLabel.setVisible(true);
                    }
                    guardarbutton.setEnabled(false); // Deshabilitar para clics dobles

                    // 2. Validar datos
                    String nombre = nombreTextField.getText();
                    String matricula = matriculaTextField.getText();
                    char[] passChars = contrasenapasswordField.getPassword();
                    String contrasena = new String(passChars);
                    String fechaNacimiento = fechadeNacimientotextField.getText();
                    String curp = curptextField.getText();
                    String tipoEstudiante = (String) tipodeEstudiantecomboBox.getSelectedItem();
                    String area = "";

                    // Validación
                    if (nombre.isEmpty() || matricula.isEmpty() || contrasena.isEmpty() || curp.isEmpty()) {
                        JOptionPane.showMessageDialog(panelPrincipal, "Complete todos los campos", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        //habilitar botón
                        if (cargandoLabel != null) {
                            cargandoLabel.setVisible(false);
                        }
                        guardarbutton.setEnabled(true);
                        return;
                    }

                    if (tipoEstudiante.equals("Residente")) {
                        area = areatextField.getText();
                        if (area.isEmpty()) {
                            JOptionPane.showMessageDialog(panelPrincipal, "El campo 'Área' es obligatorio ", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                            // habilitar botón
                            if (cargandoLabel != null) {
                                cargandoLabel.setVisible(false);
                            }
                            guardarbutton.setEnabled(true);
                            return;
                        }
                    }

                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            // operación de red o base de datos que tarda 2 segundos
                            Thread.sleep(2000);
                            return null;
                        }

                        @Override
                        protected void done() {
                            // mostrar resultado
                            if (cargandoLabel != null) {
                                cargandoLabel.setVisible(false);
                            }
                            guardarbutton.setEnabled(true); // Habilitar el botón de nuevo

                            JOptionPane.showMessageDialog(panelPrincipal, "Estudiante " + nombre + " guardado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                            // Limpiar los campos después de guardar
                            nombreTextField.setText("");
                            matriculaTextField.setText("");
                            contrasenapasswordField.setText(""); // Para JPasswordField
                            fechadeNacimientotextField.setText("");
                            curptextField.setText("");
                            areatextField.setText(""); // Si está visible
                            tipodeEstudiantecomboBox.setSelectedIndex(0); // Volver a la primera opción
                        }
                    }.execute();
                }
            });
        }
    }

    // MAIN
    public static void main(String[] args) {
        JFrame frame = new JFrame("Crear Estudiante");
        crearEstudiante formulario = new crearEstudiante();

        if (formulario.panelPrincipal == null) {
            System.out.println("");
            return;
        }

        frame.setContentPane(formulario.panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Tamaño fijo para que se vea bien
        frame.setLocationRelativeTo(null); // Centrar
        frame.setVisible(true);
    }
}