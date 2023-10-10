package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.createStrictControl;
import static org.junit.jupiter.api.Assertions.*;

class PremioTest {
    Premio premio;
    ClienteWebService cwsMock;
    float aleatorio;
    String esperado, real, resultadoObtenerPremio;
    IMocksControl control;

    @BeforeEach
    void inicializacion() {
        control = createStrictControl();
        premio = EasyMock.partialMockBuilder(Premio.class).addMockedMethod("generaNumero").mock(control);
        cwsMock = control.mock(ClienteWebService.class);
        // El atributo es público asi que se puede asignar de esta manera.
        premio.cliente = cwsMock;
    }

    @Test
    void compruebaPremioC1() {
        aleatorio = 0.07f;
        resultadoObtenerPremio = "entrada final Champions";
        esperado = "Premiado con entrada final Champions";

        EasyMock.expect(premio.generaNumero()).andReturn(aleatorio);
        assertDoesNotThrow(() -> EasyMock.expect(cwsMock.obtenerPremio()).andReturn(resultadoObtenerPremio));

        control.replay();

        real = premio.compruebaPremio();

        assertEquals(esperado, real);

        control.verify();
    }

    @Test
    void compruebaPremioC2() {
        aleatorio = 0.03f;
        esperado = "No se ha podido obtener el premio";

        EasyMock.expect(premio.generaNumero()).andReturn(aleatorio);
        // El assertDoesNotThrow se pone para engañar a Java haciendole creer que la excepción está controlada.
        assertDoesNotThrow(() -> EasyMock.expect(cwsMock.obtenerPremio()).andThrow(new ClienteWebServiceException()));

        control.replay();

        real = premio.compruebaPremio();

        assertEquals(esperado, real);

        control.verify();
    }

    @Test
    void compruebaPremioC3() {
        aleatorio = 0.3f;
        esperado = "Sin premio";

        EasyMock.expect(premio.generaNumero()).andReturn(aleatorio);
        // No se usa el mock de la dependencia externa, porque el número aleatorio es 0,3 y Premio tenemos que si (generaNumero() < PROBABILIDAD_PREMIO) devuelva dicha frase.
        control.replay();

        real = premio.compruebaPremio();

        assertEquals(esperado, real);

        control.verify();
    }
}