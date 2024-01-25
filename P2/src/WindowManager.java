import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public abstract class WindowManager extends JFrame {
    protected List<Item> itens = new ArrayList<>();
    protected JPanel mainPanel = new JPanel(new BorderLayout());

    public WindowManager() {
        super("P2");
        setSize(390, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Livro();

        setVisible(true);
    }

    protected abstract void Livro();
    protected abstract void Revista();
    protected abstract void Video();
    protected abstract void Listagem();
}
