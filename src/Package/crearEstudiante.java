package Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class crearEstudiante extends JFrame {

    // --- VARIABLES ---
    private JPanel panelPrincipal;
    private JPanel panelDatosPersonales;
    private JPanel panelDatosAcademicos;

    private JTextField nombreTextField;
    private JTextField matriculaTextField;
    private JPasswordField contrasenapasswordField;
    private JTextField fechadeNacimientotextField;
    private JTextField curptextField;
    private JComboBox EspecialidadcomboBox;

    private JPanel panelResidente;
    private JTextField areatextField;

    Consult_Database mysql;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  //boton
    private JButton guardarbutton; // El botón de guardar
    private JLabel cargandoLabel; // El texto de "Cargando..."
    private JComboBox TipoEstudianteBox;


    //  Validar Datos del formulario en caso de que falté alguno y validar el tipo de estudiante si el espacio está vacío.
    public boolean validarDatos(String nombre, String matricula, String password, String curp, String area, String tipoEstudiante) {

        // Validar vacíos en blanco
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (matricula == null || matricula.trim().isEmpty()) return false;
        if (password == null || password.trim().isEmpty()) return false;
        if (curp == null || curp.trim().isEmpty()) return false;

        //Validar estudiante residente si lo dejó en blanco
        if ("Residente".equals(tipoEstudiante)) {
            if (area == null || area.trim().isEmpty()) {
                return false;
            }
        }

        //Validar longitud de CURP
        if (curp.length() < 18) return false;

        return true;
    }

    // --- CONSTRUCTOR ---
    public crearEstudiante(Consult_Database database) {
        mysql = database;
        EspecialidadcomboBox.addItem("Medicina");
        EspecialidadcomboBox.addItem("Odontología");
        EspecialidadcomboBox.addItem("Nutrición");
        EspecialidadcomboBox.addItem("Enfermería");

        TipoEstudianteBox.addItem("Residente");
        TipoEstudianteBox.addItem("Servicio Social");
        TipoEstudianteBox.addItem("Internos");


        if (panelResidente != null) {
            panelResidente.setVisible(false);
        }

        // Para hacerlo invisible
        if (cargandoLabel != null) {
            cargandoLabel.setVisible(false);
        }


        EspecialidadcomboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String seleccion = (String) EspecialidadcomboBox.getSelectedItem();
                    if (panelResidente != null) {
                        if (seleccion.equals("Medicina")) {
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
                    String contrasena = new String(contrasenapasswordField.getPassword());
                    String fechaNacimiento = fechadeNacimientotextField.getText().trim();
                    String curp = curptextField.getText();
                    String tipoEstudiante = (String) EspecialidadcomboBox.getSelectedItem();
                    // area medicina, enfermeria, nutricion, odontologia
                    // tipo de estudiante residente-medicina y servicio social, internos-medicina
                    String area = "Medicina";

                    // Llama a tu nuevo metodo validarDatos
                    boolean esValido = validarDatos(nombre, matricula, contrasena, curp, area, tipoEstudiante);

                    if (!esValido) {
                        // Si el es falso entonces pedirá que rellenes de nuevo
                        JOptionPane.showMessageDialog(panelPrincipal, "Datos incorrectos o incompletos (Revise campos vacíos o Área de residente)", "Error", JOptionPane.ERROR_MESSAGE);

                        // Se habilita el botón de nuevo.
                        if (cargandoLabel != null) cargandoLabel.setVisible(false);
                        guardarbutton.setEnabled(true);

                        return;
                    }


                    mysql.createTuplaAlumnos(matricula,contrasena,nombre, Date.valueOf(LocalDate.parse(fechaNacimiento,dateFormat)),curp,"Residente",area);

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
                            EspecialidadcomboBox.setSelectedIndex(0); // Volver a la primera opción
                        }
                    }.execute();
                }
            });
        }
        this.setContentPane( panelPrincipal );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}