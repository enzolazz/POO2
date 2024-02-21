package managers;

import javax.swing.*;
import java.awt.*;

public class HeaderManager {
    private JPanel header;

    public HeaderManager(String nome, int size) {
        header = new JPanel(new BorderLayout());
        JLabel label = new JLabel(nome, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, size));
        header.add(label);
    }

    public JPanel getHeader() {
        return header;
    }
}
