package ppss.ejercicio1;

import java.util.Calendar;

//
// Refactorizo para poder probar una fecha concreta.
// Refactorización en la clase GestorLlamadasTestable.
//

public class GestorLlamadas {
    private static final double TARIFA_NOCTURNA=10.5;
    private static final double TARIFA_DIURNA=20.8;

    // Necesitamos controlar lo que devuelve este método y como es un método de la propia clase del SUT, se controla en un TESTABLE.
    public int getHoraActual() {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR);
        return hora;
    }

    public double calculaConsumo(int minutos) {
        int hora = getHoraActual();
        if(hora < 8 || hora > 20) {
            return minutos * TARIFA_NOCTURNA;
        } else {
            return minutos * TARIFA_DIURNA;
        }
    }
}
