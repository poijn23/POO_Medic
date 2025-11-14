package Package;
import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {
    public JButton iniciarSesion;
    public JTextField usuarioTxt;
    public JPasswordField contraseniaTxt;
    public JLabel etiquetaUsuario;
    public JLabel etiquetaContrasenia;

    public Login(){
        setTitle("Inicio de Sesion");
        setSize(350, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        etiquetaUsuario.setBounds(20, 30, 80, 25);
        centro.add(etiquetaUsuario);

        usuarioTxt.setBounds(110, 30, 200, 25);
        centro.add(usuarioTxt);

        etiquetaContrasenia.setBounds(20, 60, 80, 25);
        centro.add(etiquetaContrasenia);

        contraseniaTxt.setBounds(110, 60, 200, 25);
        centro.add(contraseniaTxt);

        iniciarSesion.setBounds(120, 100, 150, 25);
        centro.add(iniciarSesion);

        iniciarSesion.addActionListener(new LoginController(this));
        contraseniaTxt.addActionListener(new LoginController(this));

        this.add(centro, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void inicializarComponentes(){
        iniciarSesion = new JButton("Iniciar Sesion");
        usuarioTxt = new JTextField();
        contraseniaTxt = new JPasswordField();
        etiquetaUsuario = new JLabel("Usuario:");
        etiquetaContrasenia = new JLabel("Contraseña:");
    }
}
