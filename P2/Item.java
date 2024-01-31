public abstract class Item {
    protected String titulo;

    public Item(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() { return this.titulo; }

    public abstract String description();
}
