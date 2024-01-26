public class StartWindow extends Janelas {
    public StartWindow(String tipo) {
        super();
        switch (tipo) {
            case "Livros" -> {
                Livro();
                setSize(390, 220);
            }
            case "Revistas" -> {
                Revista();
                setSize(370, 220);
            }
            case "Vídeos" -> {
                Video();
                setSize(390, 220);
            }
            case "Listagem" -> {
                Listagem();
                setSize(300, 250);
            }
        }
    }
}
