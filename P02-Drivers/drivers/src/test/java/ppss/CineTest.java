package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class CineTest {
    int solicitados;
    boolean booleanReal, booleanEsperado;
    ButacasException esperadaEX;
    Cine cine;

    @BeforeEach
    public void inicializacion() {
        cine = new Cine();
    }

    //No se lanza la excepción porque en el código no se lanza.
    //Depuro-Cambio el código.
    @Test
    @Tag("normal")
    public void reservaButacasC1() {
        solicitados = 3;
        boolean asientos[] = {};
        esperadaEX = new ButacasException("No se puede procesar la solicitud");

        ButacasException ex = assertThrows(ButacasException.class, () -> cine.reservaButacasV1(asientos,solicitados));
        assertEquals(esperadaEX.getMessage(),ex.getMessage());
    }

    //El código está mal implementado para estos datos de entrada, por eso falla.
    //Depuro-Cambio el código.
    @Test
    @Tag("normal")
    public void reservaButacasC2() {
        solicitados = 0;
        boolean asientos[] = {};
        booleanEsperado = false;

        booleanReal = assertDoesNotThrow(() -> cine.reservaButacasV1(asientos,solicitados));
        assertEquals(booleanEsperado,booleanReal);
    }

    //Correcto.
    @Test
    @Tag("normal")
    public void reservaButacasC3() {
        solicitados = 2;
        boolean asientos[] = {false,false,false,true,true};
        booleanEsperado = true;

        booleanReal = assertDoesNotThrow(() -> cine.reservaButacasV1(asientos,solicitados));
        assertEquals(booleanEsperado,booleanReal);
    }

    //Correcto.
    @Test
    @Tag("normal")
    public void reservaButacasC4() {
        solicitados = 1;
        boolean asientos[] = {true,true,true};
        booleanEsperado = false;

        assertDoesNotThrow(() -> booleanReal = cine.reservaButacasV1(asientos,solicitados));
        assertEquals(booleanEsperado,booleanReal);
    }

    @ParameterizedTest
    @MethodSource("casosDePrueba")
    @Tag("parametrizado")
    public void reservaButacasC5(int solicitados, boolean[] asientos, boolean booleanEsperado) {
        booleanReal = assertDoesNotThrow(() -> cine.reservaButacasV1(asientos,solicitados));
        assertEquals(booleanEsperado,booleanReal);
    }

    private static Stream<Arguments> casosDePrueba(){
        return Stream.of(
                Arguments.of(0, new boolean[]{}, false),
                Arguments.of(2, new boolean[]{false,false,false,true,true}, true),
                Arguments.of(1, new boolean[]{true,true,true}, false)
        );
    }
}