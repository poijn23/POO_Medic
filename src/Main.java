
import Package.RegistroAsistencia;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    private static RegistroAsistencia registroAsistenciaPanel;
    private static JFrame frame;
    public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        registroAsistenciaPanel = new RegistroAsistencia();
        frame=new JFrame("Registro Asistencia");
        frame.setContentPane(registroAsistenciaPanel.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

