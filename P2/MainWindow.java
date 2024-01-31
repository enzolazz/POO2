import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("P2");
        setSize(390, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new BookWindow(this, new InputManager(this, new DataManager()));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
