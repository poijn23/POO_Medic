
import Package.MainPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    private static MainPanel mainPanel;
    private static JFrame frame;
    public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::createAndShowGUI);
    }



    private static void createAndShowGUI() {
        mainPanel= new MainPanel();
    }
}

