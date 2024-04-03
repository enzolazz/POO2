public class Main {
    public static void main(String[] args) {
        Expressao expressao;

        // LETRA A)
        expressao = new Operacao(new Numero(0));
        System.out.println(STR."Resultado da expressão: \{expressao.getResultado()}");
        // LETRA B)
        expressao = new Operacao(
                new Numero(1),
                '+',
                new Numero(2)
        );
        System.out.println(STR."Resultado da expressão: \{expressao.getResultado()}");

        // LETRA C)
        expressao = new Operacao(
                new Numero(1),
                '*',
                new Operacao(new Numero(2),
                        '+',
                        new Numero(3))
        );
        System.out.println(STR."Resultado da expressão: \{expressao.getResultado()}");

        // LETRA D)
        expressao = new Operacao(
                        new Operacao(
                            new Numero(2),
                    '*',
                            new Numero(3)),
                '+',
                        new Operacao(
                            new Numero(4),
                    '/',
                            new Operacao(
                                new Numero(5),
                        '-',
                                new Numero(3)
                                    )));

        System.out.println("Resultado da expressão: " + expressao.getResultado());
    }
}
