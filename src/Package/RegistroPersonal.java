package Package;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class RegistroPersonal extends JFrame {
    public JLabel etiquetaNombre;
    public JLabel etiquetaFecha;
    public JLabel etiquetaRoles;
    public JLabel etiquetaClave;
    public JLabel etiquetaPassword;
    public JTextField nombre;
    public JTextField fecha;
    public String[] roles = {"Seleccione una opción", "Coordinador", "Técnico", "Personal Administrativo"};
    public JComboBox<String> rolesCombo = new JComboBox<>(roles);
    public JTextField clavePersonal;
    public JPasswordField passPersonal;
    public JLabel etiquetaError;


    public JButton btnAceptar;
    public JButton btnCancelar;
    private Consult_Database myDatabase;

    public RegistroPersonal(Consult_Database myDatabase){
        this.myDatabase = myDatabase;
        setTitle("Registro de Personal");
        setSize(500,300);

        btnAceptar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> cancelar());

        inicializarComponentes();
        JPanel centro = new JPanel();
        centro.setLayout(null);

        etiquetaNombre.setBounds(20, 30, 150, 25);
        centro.add(etiquetaNombre);
        nombre.setBounds(170, 30, 150, 30);
        centro.add(nombre);

        etiquetaFecha.setBounds(20, 70, 150, 25);
        centro.add(etiquetaFecha);
        fecha.setBounds(170, 70, 150, 30);
        centro.add(fecha);

        etiquetaRoles.setBounds(20, 110, 150, 25);
        centro.add(etiquetaRoles);
        rolesCombo.setBounds(170, 110, 150, 30);
        centro.add(rolesCombo);

        etiquetaClave.setBounds(20, 150, 150, 25);
        centro.add(etiquetaClave);
        clavePersonal.setBounds(170, 150, 150, 30);
        centro.add(clavePersonal);

        etiquetaPassword.setBounds(20, 190, 150, 25);
        centro.add(etiquetaPassword);
        passPersonal.setBounds(170, 190, 150, 30);
        centro.add(passPersonal);

        btnAceptar.setBounds(170, 220, 150, 30);
        centro.add(btnAceptar);
        btnCancelar.setBounds(20, 220, 150, 30);
        centro.add(btnCancelar);

        etiquetaError.setBounds(170, 300, 200, 30);
        centro.add(etiquetaError);

        this.add(centro, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }



    public void inicializarComponentes(){
        nombre = new JTextField();
        fecha = new JTextField("dd-mm-aaaa");
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

    public boolean validarNombre(){
        if(nombre.getText().isEmpty()){
            etiquetaError.setText("Debe llenar todos los campos");
            return false;
        }
        return false;
    }

    public boolean validacion(){
        if(validarNombre()){
            return true;
        }
        return false;
    }


    public void registrar(){
        if(validacion()){
            String rolSeleccionado = (String) rolesCombo.getSelectedItem();
            String nombrePersonal = nombre.getText();
            String clave = clavePersonal.getText();
            String password = new String(passPersonal.getPassword());

            String passwordPlana = new String(passPersonal.getPassword());

            String passwordHasheada = PasswordHasher.hashPassword(passwordPlana);

            //Manejo y Conversión de Fecha (String -> java.sql.Date)
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
                    passwordHasheada
            );


            if (registroExitoso) {
                JOptionPane.showMessageDialog(this, "¡Personal registrado con éxito!\nRol: " + rolSeleccionado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el personal en la Base de Datos. Revise los logs de la consola.", "Error de BD", JOptionPane.ERROR_MESSAGE);
                etiquetaError.setText("Error en la inserción de la BD.");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Error de Validación: " + etiquetaError.getText(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void cancelar() {
    }
}
