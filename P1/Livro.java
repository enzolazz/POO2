public class Livro extends Item {
    private String autor;

    public Livro(String titulo, String autor, int ano) {
        super(titulo, ano);
        this.autor = autor;
    }

    public String getAutor() { return this.autor; }

    public String descricao() {
        return "Livro: " + titulo + ", " + autor + ", "
                + ano + '\n';
    }
}
