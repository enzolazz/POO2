public class Book extends Item {
    private String autor;
    private int ano;

    public Book(String titulo, String autor, int ano) {
        super(titulo);
        this.autor = autor;
        this.ano = ano;
    }

    public String getAutor() { return autor; }

    public int getAno() { return ano; }

    public String description() {
        return "Livro: " + titulo + ", " + autor + ", "
                + ano + '\n';
    }
}
