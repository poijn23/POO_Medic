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
        if (matricula == null || matricula.trim().isEmpty()) return false;
        if (password == null || password.trim().isEmpty()) return false;
        if (curp == null || curp.trim().isEmpty()) return false;
        if (curp.length() < 18) return false;

        return true;
    }

    // --- CONSTRUCTOR ---
    public crearEstudiante(Consult_Database database) {
        mysql = database;

        // ESPECIALIDADES
        EspecialidadcomboBox.addItem("Medicina");
        EspecialidadcomboBox.addItem("Odontología");
        EspecialidadcomboBox.addItem("Enfermería");
        EspecialidadcomboBox.addItem("Nutrición");

        //mostrar las opciones de medicina por defecto
        llenarComboTipoParaMedicina();

        if (panelResidente != null) panelResidente.setVisible(true); // Medicina es default
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
                    String matricula = matriculaTextField.getText();
                    String contrasena = new String(contrasenapasswordField.getPassword());
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
                        mysql.createTuplaAlumnos(matricula, contrasena, nombre, Date.valueOf(LocalDate.parse(fechaNacimiento, dateFormat)), curp, tipoFinal, especialidad);
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

                            JOptionPane.showMessageDialog(panelPrincipal, "Estudiante guardado:\n" + especialidad + " - " + tipoFinal, "Éxito", JOptionPane.INFORMATION_MESSAGE);

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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public static void main(String[] args) {
        // Main
        new crearEstudiante(null);
    }
}