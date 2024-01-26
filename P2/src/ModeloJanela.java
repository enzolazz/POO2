import javax.swing.*;

public class ModeloJanela extends JFrame {
    public ModeloJanela() {
        super("P2");
        setSize(390, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new JanelaLivro(this, new InputManager(this, new DataManager()));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
