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
            @Override
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

    private void Livros() {
        // Cabecalho
        JPanel tituloPagina = new JPanel(new BorderLayout());
        JLabel labelLivros = new JLabel("Livros", JLabel.CENTER);
        labelLivros.setFont(new Font("Arial", Font.BOLD, 18));
        tituloPagina.add(labelLivros);

        // Titulo
        JPanel tituloLivro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTitulo = new JLabel("Titulo:    ");
        labelTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoTitulo = new JTextField();
        campoTitulo.setColumns(20);
        tituloLivro.add(labelTitulo);
        tituloLivro.add(campoTitulo);

        // Autor
        JPanel autorLivro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelAutor = new JLabel("Autor:    ");
        labelAutor.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoAutor = new JTextField();
        campoAutor.setColumns(20);
        autorLivro.add(labelAutor);
        autorLivro.add(campoAutor);

        // Ano
        JPanel anoLivro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelAno = new JLabel("Ano:       ");
        labelAno.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoAno = new JTextField();
        campoAno.setColumns(6);
        anoLivro.add(labelAno);
        anoLivro.add(campoAno);

        // Botoes
        JPanel botoesPanel = new JPanel();
        JButton incluirBotao = new JButton("Incluir");
        incluirBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!campoTitulo.getText().isEmpty() && !campoAutor.getText().isEmpty() && !campoAno.getText().isEmpty()) {
                    itens.add(new Livro(campoTitulo.getText(), campoAutor.getText(), Integer.parseInt(campoAno.getText())));
                    campoTitulo.setText("");
                    campoAutor.setText("");
                    campoAno.setText("");
                } else {
                    JOptionPane.showMessageDialog(janela, "Por favor, insira informações válidas.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        botoesPanel.add(incluirBotao);
        botoesPanel.add(botoes("Revistas"));

        JPanel livros = new JPanel();
        livros.add(tituloLivro);
        livros.add(autorLivro);
        livros.add(anoLivro);

        mainPanel.add(tituloPagina, BorderLayout.NORTH);
        mainPanel.add(livros, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
        janela.getContentPane().add(mainPanel, BorderLayout.CENTER);
        janela.revalidate();
        janela.repaint();
    }

    private void Revistas() {
        // Cabecalho
        JPanel tituloPagina = new JPanel(new BorderLayout());
        JLabel labelRevistas = new JLabel("Revistas", JLabel.CENTER);
        labelRevistas.setFont(new Font("Arial", Font.BOLD, 18));
        tituloPagina.add(labelRevistas);

        // Titulo
        JPanel tituloRevista = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTitulo = new JLabel("Titulo: ");
        labelTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoTitulo = new JTextField();
        campoTitulo.setColumns(20);
        tituloRevista.add(labelTitulo);
        tituloRevista.add(campoTitulo);

        // Org/
        JPanel orgRevista = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelOrg = new JLabel("Org.:   ");
        labelOrg.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoOrg = new JTextField();
        campoOrg.setColumns(20);
        orgRevista.add(labelOrg);
        orgRevista.add(campoOrg);

        // Vol.
        JPanel inteirosRevista = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelVol = new JLabel("Vol.:    ");
        labelVol.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoVol = new JTextField();
        campoVol.setColumns(2);
        inteirosRevista.add(labelVol);
        inteirosRevista.add(campoVol);

        // Nro.
        JLabel labelNro = new JLabel("Nro.:    ");
        labelNro.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoNro = new JTextField();
        campoNro.setColumns(2);
        inteirosRevista.add(labelNro);
        inteirosRevista.add(campoNro);

        // Ano
        JLabel labelAno = new JLabel("Ano:    ");
        labelAno.setFont(new Font("Arial", Font.PLAIN, 12));
        JTextField campoAno = new JTextField();
        campoAno.setColumns(4);
        inteirosRevista.add(labelAno);
        inteirosRevista.add(campoAno);

        // Botoes
        JPanel botoesPanel = new JPanel();
        JButton incluirBotao = new JButton("Incluir");
        incluirBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!campoTitulo.getText().isEmpty() && !campoOrg.getText().isEmpty()
                        && !campoVol.getText().isEmpty() && !campoNro.getText().isEmpty()
                        && !campoAno.getText().isEmpty()) {
                    itens.add(new Revista(campoTitulo.getText(), campoOrg.getText(),
                            Integer.parseInt(campoVol.getText()), Integer.parseInt(campoNro.getText()),
                            Integer.parseInt(campoAno.getText())));
                    campoTitulo.setText("");
                    campoOrg.setText("");
                    campoVol.setText("");
                    campoNro.setText("");
                    campoAno.setText("");
                } else {
                    JOptionPane.showMessageDialog(janela, "Por favor, insira informações válidas.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        botoesPanel.add(incluirBotao);
        botoesPanel.add(botoes("Livros"));

        JPanel revistas = new JPanel();

        revistas.add(tituloRevista);
        revistas.add(orgRevista);
        revistas.add(inteirosRevista);

        mainPanel.add(tituloPagina, BorderLayout.NORTH);
        mainPanel.add(revistas, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);
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
