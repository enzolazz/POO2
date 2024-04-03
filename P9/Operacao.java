public class Operacao implements Expressao {

    protected Expressao op1;
    protected Expressao op2;
    private final char operation;

    public Operacao(Expressao op1, char operation,  Expressao op2) {
        this.op1 = op1;
        this.op2 = op2;
        this.operation = operation;
    }

    public Operacao(Numero numero) {
        this.op1 = numero;
        this.op2 = new Numero(0);
        this.operation = '+';
    }


    public double getResultado() {
        double result = switch (operation) {
            case '+' -> op1.getResultado() + op2.getResultado();
            case '-' -> op1.getResultado() - op2.getResultado();
            case '*' -> op1.getResultado() * op2.getResultado();
            case '/' -> op1.getResultado() / op2.getResultado();
            default -> 0;
        };

        return result;
    }
}