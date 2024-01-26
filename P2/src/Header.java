import javax.swing.*;
import java.awt.*;

public class Header {
    private JPanel header;

    public Header(String nome) {
        header = new JPanel(new BorderLayout());
        JLabel label = new JLabel(nome, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(label);
    }

    public JPanel getHeader() {
        return header;
    }
}
