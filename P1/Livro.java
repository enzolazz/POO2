public class Livro extends Item {
    String autor;

    public Livro(String titulo, String autor, int ano) {
        super(titulo, ano);
        this.autor = autor;
    }

    public String getAutor() { return this.autor; }

    public String descricao() {
        return "Livro: " + this.titulo + ", " + this.autor + ", "
                + this.ano + '\n';
    }
}
