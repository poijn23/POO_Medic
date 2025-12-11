package Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
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

    // Este es el combo principal (Medicina, Odontologia, etc.)
    private JComboBox EspecialidadcomboBox;

    // PANEL QUE SE OCULTA
    private JPanel panelResidente;

    // Este es el combo secundario (Interno, Residente) que va DENTRO del panelResidente
    private JComboBox TipoEstudianteBox;


    Consult_Database mysql;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JButton guardarbutton;
    private JLabel cargandoLabel;

    // --- Validación
    public boolean validarDatos(String nombre, String matricula, String password, String curp) {

        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (curp == null || curp.trim().isEmpty()) return false;
        if (curp.length() < 18) return false;

        return true;
    }

    // --- CONSTRUCTOR ---
    public crearEstudiante(Consult_Database database) {
        mysql = database;

        // ESPECIALIDADES
        EspecialidadcomboBox.addItem("Medicina");
        EspecialidadcomboBox.addItem("Odontologia");
        EspecialidadcomboBox.addItem("Enfermeria");
        EspecialidadcomboBox.addItem("Nutricion");

        //mostrar las opciones de medicina por defecto
        llenarComboTipoParaMedicina();

        matriculaTextField.setText("Automática");
        matriculaTextField.setEnabled(false);
        contrasenapasswordField.setText("********");
        contrasenapasswordField.setEnabled(false);
        if (panelResidente != null) panelResidente.setVisible(true);
        if (cargandoLabel != null) cargandoLabel.setVisible(false);

        EspecialidadcomboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    String seleccion = (String) EspecialidadcomboBox.getSelectedItem();

                    if (panelResidente != null) {
                        if ("Medicina".equals(seleccion)) {
                            // Si es Medicina, muestra el panel y llenamos con opciones
                            panelResidente.setVisible(true);
                            llenarComboTipoParaMedicina();
                        } else {
                            // Si es Odontología, Nutrición, etc., ocultamos el panel
                            // Automáticamente será "Servicio Social" al guardar
                            panelResidente.setVisible(false);
                        }
                    }
                    // Reajustar tamaño de ventana
                    if (panelPrincipal != null) {
                        SwingUtilities.getWindowAncestor(panelPrincipal).pack();
                    }
                }
            }
        });
        // 4. BOTÓN GUARDAR
        if (guardarbutton != null) {
            guardarbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cargandoLabel != null) cargandoLabel.setVisible(true);
                    guardarbutton.setEnabled(false);

                    // --- DATOS ---
                    String nombre = nombreTextField.getText();
                    String matricula = generarMatriculaAleatoria();
                    String contrasena = generarPasswordAleatoria();
                    String fechaNacimiento = fechadeNacimientotextField.getText().trim();
                    String curp = curptextField.getText();

                    // DATOS ACADÉMICOS
                    String especialidad = (String) EspecialidadcomboBox.getSelectedItem();
                    String tipoEstudiante = ""; // Aquí guardar si es Residente, Interno o SS

                    if (especialidad.equals("Medicina")) {
                        tipoEstudiante = (String) TipoEstudianteBox.getSelectedItem();
                    } else {
                        // Si NO es medicina, forzamos a que sea Servicio Social
                        tipoEstudiante = "Servicio Social";
                    }

                    // --- VALIDACIÓN ---
                    if (!validarDatos(nombre, matricula, contrasena, curp)) {
                        JOptionPane.showMessageDialog(panelPrincipal, "Datos incompletos o CURP inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                        if (cargandoLabel != null) cargandoLabel.setVisible(false);
                        guardarbutton.setEnabled(true);
                        return;
                    }

                    final String tipoFinal = tipoEstudiante;

                    try {
                        mysql.createTuplaAlumnos(matricula, contrasena, nombre, Date.valueOf(LocalDate.parse(fechaNacimiento, dateFormat)), curp, especialidad, tipoFinal);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panelPrincipal, "Error al guardar en BD o fecha inválida: " + ex.getMessage());
                        guardarbutton.setEnabled(true);
                        if (cargandoLabel != null) cargandoLabel.setVisible(false);
                        return;
                    }

                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            Thread.sleep(1500); // Simulación
                            return null;
                        }

                        @Override
                        protected void done() {
                            if (cargandoLabel != null) cargandoLabel.setVisible(false);
                            guardarbutton.setEnabled(true);

                            JOptionPane.showMessageDialog(panelPrincipal,
                                    "¡Estudiante guardado con éxito!\n\n" +
                                            "Matrícula Asignada: " + matricula + "\n" +
                                            "Contraseña Generada: " + contrasena + "\n\n" +
                                            "(Anote estos datos)",
                                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

                            // Limpiar campos
                            nombreTextField.setText("");
                            matriculaTextField.setText("");
                            contrasenapasswordField.setText("");
                            fechadeNacimientotextField.setText("");
                            curptextField.setText("");
                            EspecialidadcomboBox.setSelectedIndex(0); // Volver a Medicina
                        }
                    }.execute();
                }
            });
        }

        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600, 500); // Tamaño fijo recomendado
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // --- llenar medicina
    private void llenarComboTipoParaMedicina() {
        TipoEstudianteBox.removeAllItems(); // Limpiamos lo que haya
        TipoEstudianteBox.addItem("Interno");
        TipoEstudianteBox.addItem("Residente");
        TipoEstudianteBox.addItem("Servicio social");
        // Si medicina también acepta servicio social, descomenta la siguiente linea:
        // TipoEstudianteBox.addItem("Servicio Social");
    }

    // Generador simple de matrícula
    private String generarMatriculaAleatoria() {
        int numero = (int) (Math.random() * 9000) + 1000; // Número entre 1000 y 9999
        return "ES-" + Year.now().getValue() + "-" + numero;
    }

    // Generador simple de contraseña
    private String generarPasswordAleatoria() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) { // 8 caracteres
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelDatosPersonales = new JPanel();
        panelDatosPersonales.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panelDatosPersonales, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Nombre");
        panelDatosPersonales.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 17), null, 0, false));
        nombreTextField = new JTextField();
        nombreTextField.setText("");
        panelDatosPersonales.add(nombreTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 34), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Matricula");
        panelDatosPersonales.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 17), null, 0, false));
        matriculaTextField = new JTextField();
        panelDatosPersonales.add(matriculaTextField, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 34), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Contraseña");
        panelDatosPersonales.add(label3, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 17), null, 0, false));
        contrasenapasswordField = new JPasswordField();
        panelDatosPersonales.add(contrasenapasswordField, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 34), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Fecha de Nacimiento (yyyy-MM-dd)");
        panelDatosPersonales.add(label4, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 17), null, 0, false));
        fechadeNacimientotextField = new JTextField();
        panelDatosPersonales.add(fechadeNacimientotextField, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 34), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("CURP");
        panelDatosPersonales.add(label5, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 17), null, 0, false));
        curptextField = new JTextField();
        curptextField.setText("");
        panelDatosPersonales.add(curptextField, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(628, 34), null, 0, false));
        panelDatosAcademicos = new JPanel();
        panelDatosAcademicos.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelPrincipal.add(panelDatosAcademicos, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Especialidad");
        panelDatosAcademicos.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        EspecialidadcomboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        EspecialidadcomboBox.setModel(defaultComboBoxModel1);
        panelDatosAcademicos.add(EspecialidadcomboBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelResidente = new JPanel();
        panelResidente.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelDatosAcademicos.add(panelResidente, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Tipo de estudiante");
        panelResidente.add(label7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TipoEstudianteBox = new JComboBox();
        panelResidente.add(TipoEstudianteBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        guardarbutton = new JButton();
        guardarbutton.setText("Guardar Estudiante");
        panelPrincipal.add(guardarbutton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cargandoLabel = new JLabel();
        cargandoLabel.setText("Cargando...");
        cargandoLabel.setVisible(false);
        panelPrincipal.add(cargandoLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label6.setLabelFor(EspecialidadcomboBox);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelPrincipal;
    }


    // MAIN
   // public static void main(String[] args) {
     //   Consult_Database conexionPrueba = new Consult_Database();
        // new crearEstudiante(conexionPrueba);
    //}
}