public class Video extends Item {
    private String autor;
    private double duracao;

    public Video(String titulo, String autor, double duracao) {
        super(titulo);
        this.autor = autor;
        this.duracao = duracao;
    }

    public String getAutor() { return autor; }

    public double getDuracao() { return duracao; }

    public String descricao() {
        return "Video: " + titulo + ", " + autor + ", "
                + duracao + '\n';
    }
}
