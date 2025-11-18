package Package;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener{
    Consult_Database con = new Consult_Database();
    private Login login;
    public LoginController(Login login) {
        this.login = login;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String usuario = login.usuarioTxt.getText();
        String contrasenia = new String(login.contraseniaTxt.getPassword());

        final String usuarioTest = "prueba";
        final String contraseniaTest = "12345";

        if (usuarioTest.isEmpty() || contraseniaTest.isEmpty()) {
            showError("Por favor, ingrese usuario y contraseña.");
            return;
        }

        if(usuario.equals(usuarioTest) && contrasenia.equals(contraseniaTest)){
                JOptionPane.showMessageDialog(login, "Acceso (PRUEBA) concedido. Cambiando de ventana.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            login.dispose();

            RegistroAsistencia registroAsistenciaPanel = new RegistroAsistencia(con);
            JFrame frameAsistencia = new JFrame("Registro de Asistencia");

            frameAsistencia.setContentPane(registroAsistenciaPanel.panel1);
            frameAsistencia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameAsistencia.pack();
            frameAsistencia.setLocationRelativeTo(null);
            frameAsistencia.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(login, "Usuario o contraseña de prueba incorrectos.",
                    "Error de Prueba", JOptionPane.ERROR_MESSAGE);
            login.contraseniaTxt.setText("");
        }

        /*
        try{
            esto aplicaria solo cuando tengamos la clase conexionBD y la bd esté lista
            String contraseniaHasheadaBD = ConexionBD.obtenerContraseniaBD(usuario);
            if(contraseniaHasheadaBD != null){
                if(PasswordHasher.verificarContraseña(contrasenia, contraseniaHasheadaBD)){
                    JOptionPane.showMessageDialog(login, "Acceso concedido", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    login.dispose();
                }
            }else {
            showError("Contraseña incorrecta.");
        }


        }catch(Exception ex){
            System.err.println("Erro durante la verificacion del login" + ex.getMessage());
            showError("Error en el sistema. Intentelo de nuevo");
        }
        */

    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(login, message, "Error de Acceso", JOptionPane.ERROR_MESSAGE);
        login.contraseniaTxt.setText(""); // Limpiar la contraseña
    }
}
