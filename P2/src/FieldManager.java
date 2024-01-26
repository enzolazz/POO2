import javax.swing.*;
import java.awt.*;

public class FieldManager {
    private JPanel painel;

    public FieldManager(JPanel painel) {
        this.painel = painel;
    }

    public void inputField(String nome, int colunas) {
        JLabel label = new JLabel(nome + ":    ");
        JTextField campo = new JTextField();
        campo.setColumns(colunas);

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painel.add(label);
        painel.add(campo);

        this.painel.add(painel);
    }

    public JPanel getPainel() { return painel; }
}
