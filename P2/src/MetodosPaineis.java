import javax.swing.*;
import java.awt.*;

public class MetodosPaineis extends MetodosBD {
    protected JPanel inputFields(String nome, int colunas) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(nome + ":    ");
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        JTextField campo = new JTextField();
        campo.setColumns(colunas);

        painel.add(label);
        painel.add(campo);

        return painel;
    }

    protected JPanel cabecalho(String nome) {
        JPanel painel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(nome, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(label);

        return painel;
    }
}
