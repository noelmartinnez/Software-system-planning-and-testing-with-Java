package ppss.ejercicio2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    int minutos, hora;
    double esperado, real;
    GestorLlamadasTestable g;
    CalendarioStub c;

    @BeforeEach
    public void inicializacion() {
        g = new GestorLlamadasTestable();
        c = new CalendarioStub();
    }

    @Test
    void calculaConsumoC1() {
        minutos = 10;
        hora = 15;
        esperado = 208;

        c.setHoraActual(hora);
        g.setCalendario(c);

        real = g.calculaConsumo(minutos);

        assertEquals(esperado, real);
    }

    @Test
    void calculaConsumoC2() {
        minutos = 10;
        hora = 22;
        esperado = 105;

        c.setHoraActual(hora);
        g.setCalendario(c);

        real = g.calculaConsumo(minutos);

        assertEquals(esperado, real);
    }
}