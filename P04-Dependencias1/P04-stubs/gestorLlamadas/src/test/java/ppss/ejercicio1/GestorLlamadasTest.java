package ppss.ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorLlamadasTest {
    GestorLlamadasTestable g;
    int minutos, hora;
    double esperado, real;

    @BeforeEach
    public void inicializacion() {
        g = new GestorLlamadasTestable();
    }

    @Test
    void calculaConsumoC1() {
        minutos = 10;
        hora = 15;
        esperado = 208;

        g.setHoraActual(hora);

        real = g.calculaConsumo(minutos);
        assertEquals(esperado, real);
    }
    @Test
    void calculaConsumoC2() {
        minutos = 10;
        hora = 22;
        esperado = 105;

        g.setHoraActual(hora);

        real = g.calculaConsumo(minutos);
        assertEquals(esperado, real);
    }
}
