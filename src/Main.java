
import Package.RegistroAsistencia_metodos;
import Package.RegistroAsistencia;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    private static RegistroAsistencia registroAsistenciaPanel;
    private static RegistroAsistencia_metodos  registroAsistenciaMetodos;
    private static JFrame frame;
    public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        registroAsistenciaPanel = new RegistroAsistencia();
        registroAsistenciaMetodos = new RegistroAsistencia_metodos(registroAsistenciaPanel);
        registroAsistenciaPanel.confirmarButton.addActionListener(registroAsistenciaMetodos);
        registroAsistenciaPanel.confirmarButton.setActionCommand("Confirmar");
        frame=new JFrame("Registro Asistencia");
        frame.setContentPane(registroAsistenciaPanel.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

