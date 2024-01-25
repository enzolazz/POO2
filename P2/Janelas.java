import javax.swing.*;
import java.awt.*;

public class Janelas extends WindowManager {
    public Janelas() {
        super();
    }

    protected void Livro() {
        JPanel text = new JPanel(new FlowLayout());
        text.add(inputFields("Título", 20));
        text.add(inputFields("Autor", 20));
        text.add(inputFields("Ano", 4));

        JPanel botoes = new JPanel(new FlowLayout());
        JButton incluir = botaoIncluir(text, "Livro");

        botoes.add(incluir);
        botoes.add(botaoNavegacao("Revistas"));
        botoes.add(botaoNavegacao("Vídeos"));
        botoes.add(botaoListagem());


        mainPanel.add(cabecalho("Livros"), BorderLayout.NORTH);
        mainPanel.add(text, BorderLayout.CENTER);
        mainPanel.add(botoes, BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    protected void Revista() {
        JPanel text = new JPanel(new FlowLayout());
        text.add(inputFields("Título", 20));
        text.add(inputFields("Org.", 20));
        text.add(inputFields("Vol.", 2));
        text.add(inputFields("Nro.", 2));
        text.add(inputFields("Ano", 4));

        JPanel botoes = new JPanel(new FlowLayout());
        JButton incluir = botaoIncluir(text, "Revista");

        botoes.add(incluir);
        botoes.add(botaoNavegacao("Livros"));
        botoes.add(botaoNavegacao("Vídeos"));
        botoes.add(botaoListagem());

        mainPanel.add(cabecalho("Revistas"), BorderLayout.NORTH);
        mainPanel.add(text, BorderLayout.CENTER);
        mainPanel.add(botoes, BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    protected void Video() {
        JPanel text = new JPanel(new FlowLayout());
        text.add(inputFields("Título", 20));
        text.add(inputFields("Autor", 20));
        text.add(inputFields("Duração", 6));

        JPanel botoes = new JPanel(new FlowLayout());
        JButton incluir = botaoIncluir(text, "Vídeo");

        botoes.add(incluir);
        botoes.add(botaoNavegacao("Livros"));
        botoes.add(botaoNavegacao("Revistas"));
        botoes.add(botaoListagem());

        mainPanel.add(cabecalho("Vídeos"), BorderLayout.NORTH);
        mainPanel.add(text, BorderLayout.CENTER);
        mainPanel.add(botoes, BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    protected void Listagem() {
        JLabel labelCabecalho = new JLabel("Listagem", JLabel.CENTER);
        labelCabecalho.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel manager = new JPanel(new FlowLayout());
        JTextArea lista = new JTextArea();
        for (Item item : itens) {
            lista.append(item.descricao());
        }
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize(new Dimension(250, 110));
        manager.add(scrollPane);

        JPanel botoes = new JPanel();
        botoes.add(botaoNavegacao("Livros"));
        botoes.add(botaoNavegacao("Revistas"));
        botoes.add(botaoNavegacao("Vídeos"));

        mainPanel.add(labelCabecalho, BorderLayout.NORTH);
        mainPanel.add(manager);
        mainPanel.add(botoes, BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
