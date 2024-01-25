public class Livro extends Item {
    private String autor;
    private int ano;

    public Livro(String titulo, String autor, int ano) {
        super(titulo);
        this.autor = autor;
        this.ano = ano;
    }

    public String getAutor() { return autor; }

    public int getAno() { return ano; }

    public String descricao() {
        return "Livro: " + titulo + ", " + autor + ", "
                + ano + '\n';
    }
}
