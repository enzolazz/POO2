public abstract class Item {
    protected String titulo;
    protected int ano;

    public Item(String titulo, int ano) {
        this.titulo = titulo;
        this.ano = ano;
    }

    public String getTitulo() { return this.titulo; }

    public int getAno() { return this.ano; }

    public abstract String descricao();
}
