package ppss;

import org.easymock.IMocksControl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.easymock.EasyMock;

import static org.easymock.EasyMock.createStrictControl;
import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    int minutos, hora;
    double esperado, real;
    GestorLlamadas gl;
    Calendario cMock;
    IMocksControl control;

    @BeforeEach
    void inicializacion(){
        control = createStrictControl();
        gl = EasyMock.partialMockBuilder(GestorLlamadas.class).addMockedMethod("getCalendario").mock(control);
        cMock = control.mock(Calendario.class);
        EasyMock.expect(gl.getCalendario()).andReturn(cMock);
    }

    @Test
    void calculaConsumoC1() {
        minutos = 22;
        hora = 10;
        esperado = 457.6;

        EasyMock.expect(cMock.getHoraActual()).andReturn(hora);

        control.replay();

        real = gl.calculaConsumo(minutos);

        assertEquals(esperado,real);

        control.verify();
    }

    @Test
    void calculaConsumoC2() {
        minutos = 13;
        hora = 21;
        esperado = 136.5;

        EasyMock.expect(cMock.getHoraActual()).andReturn(hora);

        control.replay();

        real = gl.calculaConsumo(minutos);

        assertEquals(esperado,real);

        control.verify();
    }
}