
import Package.RegistroAsistencia;
import Package.SubirConstancia;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Package.Login;

public class Main {
    private static RegistroAsistencia registroAsistenciaPanel;
    private static JFrame frame;
    public static void main(String[] args) {
        SubirConstancia subirConstancia = new SubirConstancia();
        subirConstancia.setVisible(true);
        subirConstancia.setLocationRelativeTo(null);
        /*Login login = new Login();
        login.setVisible(true);
        login.setLocationRelativeTo(null);*/
    // SwingUtilities.invokeLater(Main::createAndShowGUI);
    }


    /*
    private static void createAndShowGUI() {
        registroAsistenciaPanel = new RegistroAsistencia();
        frame=new JFrame("Registro Asistencia");
        frame.setContentPane(registroAsistenciaPanel.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    } */
}

