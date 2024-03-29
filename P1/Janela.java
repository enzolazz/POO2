import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Janela {
    JFrame janela;
    List<Item> itens = new ArrayList<>();
    JPanel mainPanel =  new JPanel(new BorderLayout());

    public Janela() {
        janela = new JFrame("P1");
        janela.setSize(300,220);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        Livros();

        janela.setVisible(true);
    }

    public static void main(String[] args) {
        new Janela();
    }

    private JPanel botoes(String tipo) {
        JButton tipoBotao = new JButton(tipo);
        tipoBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                if (tipo.equals("Livros")) { Livros(); }
                else { Revistas(); };
            }
        });

        JButton listagemBotao = new JButton("Listagem");
        listagemBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                Listar();
            }
        });

        JPanel painelB = new JPanel();
        painelB.add(tipoBotao);
        painelB.add(listagemBotao);

        return painelB;
    }

    private JPanel inputFields(String nome, int colunas) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(nome +":    ");
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        JTextField campo = new JTextField();
        campo.setColumns(colunas);

        painel.add(label);
        painel.add(campo);

        return painel;
    }

    private JPanel cabecalho(String nome) {
        JPanel painel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(nome, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(label);

        return painel;
    }

    private void Livros() {
        JPanel titulo = inputFields("Titulo", 20);
        JPanel autor = inputFields("Autor", 20);
        JPanel ano = inputFields("Ano", 4);

        JPanel painelBotoes = new JPanel();
        JButton incluir = new JButton("Incluir");
        incluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField textoTitulo = (JTextField) titulo.getComponents()[1];
                JTextField textoAutor = (JTextField) autor.getComponents()[1];
                JTextField textoAno = (JTextField) ano.getComponents()[1];

                if (textoTitulo.getText().isEmpty() || textoAutor.getText().isEmpty() || textoAno.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(janela, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int ano = Integer.parseInt(textoAno.getText());
                        itens.add(new Livro(textoTitulo.getText(), textoAutor.getText(), ano));
                        textoTitulo.setText("");
                        textoAutor.setText("");
                        textoAno.setText("");
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(janela, "Por favor, insira uma data válida.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        JPanel livros = new JPanel();
        livros.add(titulo);
        livros.add(autor);
        livros.add(ano);

        painelBotoes.add(incluir);
        painelBotoes.add(botoes("Revistas"));


        mainPanel.add(cabecalho("Livros"), BorderLayout.NORTH);
        mainPanel.add(livros, BorderLayout.CENTER);
        mainPanel.add(painelBotoes, BorderLayout.SOUTH);
        janela.getContentPane().add(mainPanel, BorderLayout.CENTER);
        janela.revalidate();
        janela.repaint();
    }

    private void Revistas() {
        JPanel titulo = inputFields("Titulo", 20);
        JPanel org = inputFields("Org.", 20);
        JPanel vol = inputFields("Vol.", 2);
        JPanel nro = inputFields("Nro.", 2);
        JPanel ano = inputFields("Ano", 4);

        JPanel painelBotoes = new JPanel();
        JButton incluir = new JButton("Incluir");
        incluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField textoTitulo = (JTextField) titulo.getComponents()[1];
                JTextField textoOrg = (JTextField) org.getComponents()[1];
                JTextField textoVol = (JTextField) vol.getComponents()[1];
                JTextField textoNro = (JTextField) nro.getComponents()[1];
                JTextField textoAno = (JTextField) ano.getComponents()[1];

                if (textoTitulo.getText().isEmpty() || textoOrg.getText().isEmpty() || textoVol.getText().isEmpty()
                    || textoNro.getText().isEmpty() || textoAno.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(janela, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int vol = Integer.parseInt(textoVol.getText());
                        int nro = Integer.parseInt(textoNro.getText());
                        int ano = Integer.parseInt(textoAno.getText());
                        itens.add(new Revista(textoTitulo.getText(), textoOrg.getText(), vol, nro, ano));
                        textoTitulo.setText("");
                        textoOrg.setText("");
                        textoVol.setText("");
                        textoNro.setText("");
                        textoAno.setText("");
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(janela, "Por favor, insira números válidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        painelBotoes.add(incluir);
        painelBotoes.add(botoes("Livros"));

        JPanel revistas = new JPanel();

        revistas.add(titulo);
        revistas.add(org);
        revistas.add(vol);
        revistas.add(nro);
        revistas.add(ano);

        mainPanel.add(cabecalho("Revistas"), BorderLayout.NORTH);
        mainPanel.add(revistas, BorderLayout.CENTER);
        mainPanel.add(painelBotoes, BorderLayout.SOUTH);
        janela.getContentPane().add(mainPanel, BorderLayout.CENTER);
        janela.revalidate();
        janela.repaint();
    }

    private void Listar() {
        JLabel labelCabecalho = new JLabel("Listagem", JLabel.CENTER);
        labelCabecalho.setFont(new Font("Arial", Font.BOLD, 18));

        // TextArea
        JPanel manager = new JPanel(new FlowLayout());
        JTextArea lista = new JTextArea();
        for (Item item : itens) {
            lista.append(item.descricao());
        }
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize(new Dimension(250, 110));
        manager.add(scrollPane);
        // Botoes
        JPanel botoesPanel = new JPanel();
        JButton livrosBotao = new JButton("Livros");
        livrosBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                Livros();
            }
        });

        JButton revistasBotao = new JButton("Revistas");
        revistasBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                Revistas();
            }
        });
        botoesPanel.add(livrosBotao);
        botoesPanel.add(revistasBotao);

        mainPanel.add(labelCabecalho, BorderLayout.NORTH);
        mainPanel.add(manager);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        janela.getContentPane().add(mainPanel, BorderLayout.CENTER);
        janela.revalidate();
        janela.repaint();
    }
}
