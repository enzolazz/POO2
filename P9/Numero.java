public class Numero implements Expressao {
    private double valor;

    public Numero(double valor) {
        this.valor = valor;
    }

    public double getResultado() {
        return valor;
    }
}
