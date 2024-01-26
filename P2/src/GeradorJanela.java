import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GeradorJanela extends JFrame {
    protected List<Item> itens = new ArrayList<>();
    protected JPanel mainPanel = new JPanel(new BorderLayout());

    public GeradorJanela() {
        super("P2");
        setSize(390, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
