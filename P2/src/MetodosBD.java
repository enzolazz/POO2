import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MetodosBD extends GeradorJanela {
    public MetodosBD() {
        super();
    }

    protected List<JTextField> incluir(JPanel painel) {
        List<JTextField> campos = new ArrayList<>();
        for (Component grid : painel.getComponents()) {
            campos.add((JTextField) ((JPanel) grid).getComponents()[1]);
        }

        for (JTextField campo : campos) {
            if (campo.getText().isEmpty()) {
                return new ArrayList<>();
            }
        }

        return campos;
    }
}
