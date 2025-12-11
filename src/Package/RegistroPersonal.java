package Package;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
// Importamos utilidades para fecha, SQL y regex
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Date; // Necesario para la BD

public class RegistroPersonal extends JFrame {

    public JLabel etiquetaNombre;
    public JLabel etiquetaFecha;
    public JLabel etiquetaRoles;
    public JLabel etiquetaClave;
    public JLabel etiquetaPassword;
    public JTextField nombre;
    public JTextField fecha;
    public String[] roles = {"Seleccione una opción", "Coordinador", "Tecnico", "Administrativo"};
    public JComboBox<String> rolesCombo = new JComboBox<>(roles);
    public JTextField clavePersonal;
    public JPasswordField passPersonal;
    public JLabel etiquetaError;
    public JButton btnAceptar;
    public JButton btnCancelar;

    private Consult_Database myDatabase;

    public RegistroPersonal(Consult_Database myDatabase) {
        this.myDatabase = myDatabase;
        setTitle("Registro de Personal");
        setSize(500, 350);
        inicializarComponentes();

        btnAceptar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> cancelar());

        JPanel centro = new JPanel();
        centro.setLayout(null);

        etiquetaNombre.setBounds(20, 30, 150, 25); centro.add(etiquetaNombre);
        nombre.setBounds(170, 30, 150, 30); centro.add(nombre);

        etiquetaFecha.setBounds(20, 70, 150, 25); centro.add(etiquetaFecha);
        fecha.setBounds(170, 70, 150, 30); centro.add(fecha);

        etiquetaRoles.setBounds(20, 110, 150, 25); centro.add(etiquetaRoles);
        rolesCombo.setBounds(170, 110, 150, 30); centro.add(rolesCombo);

        etiquetaClave.setBounds(20, 150, 150, 25); centro.add(etiquetaClave);
        clavePersonal.setBounds(170, 150, 150, 30); centro.add(clavePersonal);

        etiquetaPassword.setBounds(20, 190, 150, 25); centro.add(etiquetaPassword);
        passPersonal.setBounds(170, 190, 150, 30); centro.add(passPersonal);

        btnAceptar.setBounds(170, 220, 150, 30); centro.add(btnAceptar);
        btnCancelar.setBounds(20, 220, 150, 30); centro.add(btnCancelar);

        etiquetaError.setBounds(20, 260, 400, 30);
        etiquetaError.setForeground(Color.RED);
        centro.add(etiquetaError);

        this.add(centro, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void inicializarComponentes() {
        nombre = new JTextField();
        fecha = new JTextField("dd-MM-yyyy");
        rolesCombo = new JComboBox<>(roles);
        clavePersonal = new JTextField();
        passPersonal = new JPasswordField();
        etiquetaNombre = new JLabel("Nombre: ");
        etiquetaFecha = new JLabel("Fecha de nacimiento: ");
        etiquetaRoles = new JLabel("Rol: ");
        etiquetaClave = new JLabel("Clave personal: ");
        etiquetaPassword = new JLabel("Contraseña: ");
        btnCancelar = new JButton("Cancelar");
        btnAceptar = new JButton("Registrar");
        etiquetaError = new JLabel("");
    }

    public boolean validarFormulario() {
        etiquetaError.setText("");

        String nombreTxt = nombre.getText().trim();
        if (nombreTxt.isEmpty()) {
            mostrarError("El nombre es obligatorio", nombre);
            return false;
        }
        if (!nombreTxt.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            mostrarError("El nombre solo debe contener letras", nombre);
            return false;
        }

        String fechaTxt = fecha.getText().trim();
        if (fechaTxt.isEmpty() || fechaTxt.equals("dd-MM-yyyy")) {
            mostrarError("Ingrese la fecha de nacimiento", fecha);
            return false;
        }
        if (!validarFormatoFecha(fechaTxt)) {
            mostrarError("Formato de fecha inválido (use dd-MM-yyyy)", fecha);
            return false;
        }

        if (rolesCombo.getSelectedIndex() == 0) {
            etiquetaError.setText("Debe seleccionar un rol válido");
            rolesCombo.requestFocus();
            return false;
        }

        String claveTxt = clavePersonal.getText().trim();
        if (claveTxt.isEmpty()) {
            mostrarError("La clave es obligatoria", clavePersonal);
            return false;
        }
        if (!claveTxt.matches("\\d+")) {
            mostrarError("La clave debe ser numérica", clavePersonal);
            return false;
        }

        char[] pass = passPersonal.getPassword();
        if (pass.length == 0) {
            mostrarError("La contraseña es obligatoria", passPersonal);
            return false;
        }
        if (pass.length < 5) {
            mostrarError("La contraseña debe tener al menos 5 caracteres", passPersonal);
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje, JComponent componente) {
        etiquetaError.setText(mensaje);
        componente.requestFocus();
    }

    private boolean validarFormatoFecha(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void registrar() {
        if (validarFormulario()) {

            String rolSeleccionado = (String) rolesCombo.getSelectedItem();
            String nombrePersonal = nombre.getText().trim();
            String clave = clavePersonal.getText().trim();

            String passwordPlana = new String(passPersonal.getPassword());
            String passwordHasheada = PasswordHasher.hashPassword(passwordPlana);

            //Manejo y Conversión de Fecha (String -> java.sql.Date)
            //Manejo y Conversión de Fecha String -> java.sql.Date
            java.sql.Date fechaSQL;
            try {

                java.util.Date fechaUtil = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(fecha.getText());
                fechaSQL = new java.sql.Date(fechaUtil.getTime());
            } catch (java.text.ParseException e) {
                etiquetaError.setText("Error: Formato de fecha inválido (DD-MM-AAAA).");
                JOptionPane.showMessageDialog(this, "Error: Formato de fecha inválido. Use DD-MM-AAAA.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean registroExitoso = myDatabase.createTuplaPersonal(
                    nombrePersonal,
                    fechaSQL,
                    rolSeleccionado,
                    clave,
                    passwordPlana
            );

            if (registroExitoso) {
                JOptionPane.showMessageDialog(this,
                        "¡Personal registrado con éxito!\nRol: " + rolSeleccionado,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar en la Base de Datos.\nRevise los logs.",
                        "Error de BD", JOptionPane.ERROR_MESSAGE);
                etiquetaError.setText("Error en la inserción de la BD.");
            }

        } else {
            JOptionPane.showMessageDialog(this,
                    "Error de Validación: " + etiquetaError.getText(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void limpiarCampos() {
        nombre.setText("");
        fecha.setText("dd-MM-yyyy");
        rolesCombo.setSelectedIndex(0);
        clavePersonal.setText("");
        passPersonal.setText("");
        etiquetaError.setText("");
    }

    public void cancelar() {
        System.exit(0);
    }
}