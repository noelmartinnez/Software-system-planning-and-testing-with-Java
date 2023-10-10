package ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultipathExampleTest {
    int a,b,c,esperado,real;
    MultipathExample me;

    @BeforeEach
    void inicializacion() {
        me = new MultipathExample();
    }

    @Test
    void multiPath1_C1() {
        a = 6;
        b = 6;
        c = 0;
        esperado = 12;

        real = me.multiPath1(a,b,c);

        assertEquals(esperado,real);
    }

    @Test
    void multiPath1_C2() {
        a = 5;
        b = 5;
        c = 0;
        esperado = 0;

        real = me.multiPath1(a,b,c);

        assertEquals(esperado,real);
    }

    @Test
    void multiPath1_C3() {
        a = 3;
        b = 6;
        c = 2;
        esperado = 8;

        real = me.multiPath1(a,b,c);

        assertEquals(esperado,real);
    }

    @ParameterizedTest
    @MethodSource("multiPath2")
    void multiPath2_C1(int a, int b, int c, int esperado){
        assertEquals(esperado, me.multiPath2(a,b,c));
    }

    @ParameterizedTest
    @MethodSource("multiPath3")
    void multiPath3_C1(int a, int b, int c, int esperado){
        assertEquals(esperado, me.multiPath3(a,b,c));
    }

    private static Stream<Arguments> multiPath2(){
        return Stream.of(
                Arguments.of(6,4,6,16),
                Arguments.of(5,5,0,0),
                Arguments.of(6,5,6,11)
        );
    }

    private static Stream<Arguments> multiPath3(){
        return Stream.of(
                Arguments.of(6,4,6,16),
                Arguments.of(5,5,0,0),
                Arguments.of(6,5,6,11)
        );
    }
}