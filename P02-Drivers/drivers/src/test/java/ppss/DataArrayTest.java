package ppss;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class DataArrayTest {
    int borrar;
    int numEsperado;
    DataArray da;
    DataException esperadaEX;

    @Test
    @Tag("normal")
    public void deleteC1() {
        borrar = 5;
        int coleccion[] = {1,3,5,7};

        numEsperado = 3;
        int colEsperada[] = {1,3,7};

        da = new DataArray(coleccion);
        assertDoesNotThrow(() -> da.delete(borrar));
        // Muy importante esto.
        assertArrayEquals(colEsperada,da.getColeccion());
        assertEquals(numEsperado,da.size());
    }

    @Test
    @Tag("normal")
    public void deleteC2() {
        borrar = 3;
        int coleccion[] = {1,3,3,5,7};

        numEsperado = 4;
        int colEsperada[] = {1,3,5,7};

        da = new DataArray(coleccion);
        assertDoesNotThrow(() -> da.delete(borrar));
        assertArrayEquals(colEsperada,da.getColeccion());
        assertEquals(numEsperado,da.size());
    }

    @Test
    @Tag("normal")
    public void deleteC3() {
        borrar = 4;
        int coleccion[] = {1,2,3,4,5,6,7,8,9,10};

        numEsperado = 9;
        int colEsperada[] = {1,2,3,5,6,7,8,9,10};

        da = new DataArray(coleccion);
        assertDoesNotThrow(() -> da.delete(borrar));
        assertArrayEquals(colEsperada,da.getColeccion());
        assertEquals(numEsperado,da.size());
    }

    @Test
    @Tag("normal")
    public void deleteC4() {
        borrar = 8;
        int coleccion[] = {};

        esperadaEX = new DataException("No hay elementos en la colección");

        da = new DataArray(coleccion);
        DataException ex = assertThrows(DataException.class, () -> da.delete(borrar));
        assertEquals(esperadaEX.getMessage(),ex.getMessage());
    }

    @Test
    @Tag("normal")
    public void deleteC5() {
        borrar = -5;
        int coleccion[] = {1,3,5,7};

        esperadaEX = new DataException("El valor a borrar debe ser > 0");

        da = new DataArray(coleccion);
        DataException ex = assertThrows(DataException.class, () -> da.delete(borrar));
        assertEquals(esperadaEX.getMessage(),ex.getMessage());
    }

    @Test
    @Tag("normal")
    public void deleteC6() {
        borrar = 0;
        int coleccion[] = {};

        esperadaEX = new DataException("Colección vacía. Y el valor a borrar debe ser > 0");

        da = new DataArray(coleccion);
        DataException ex = assertThrows(DataException.class, () -> da.delete(borrar));
        assertEquals(esperadaEX.getMessage(),ex.getMessage());
    }

    @Test
    @Tag("normal")
    public void deleteC7() {
        borrar = 8;
        int coleccion[] = {1,3,5,7};

        esperadaEX = new DataException("Elemento no encontrado");

        da = new DataArray(coleccion);
        DataException ex = assertThrows(DataException.class, () -> da.delete(borrar));
        assertEquals(esperadaEX.getMessage(),ex.getMessage());
    }

    @ParameterizedTest
    @MethodSource("casosDePruebaC8")
    @Tag("parametrizado")
    @Tag("conExcepciones")
    public void deleteC8(int borrar, int[] coleccion, String Esperado) {
        da = new DataArray(coleccion);
        DataException ex = assertThrows(DataException.class, () -> da.delete(borrar));
        assertEquals(Esperado,ex.getMessage());
    }

    @ParameterizedTest
    @MethodSource("casosDePruebaC9")
    @Tag("parametrizado")
    public void deleteC9(int borrar, int[] coleccion, int numEsperado, int[] colEsperada) {
        da = new DataArray(coleccion);
        assertDoesNotThrow(() -> da.delete(borrar));
        assertEquals(numEsperado,da.size());
        assertArrayEquals(colEsperada,da.getColeccion());
    }

    private static Stream<Arguments> casosDePruebaC8(){
        return Stream.of(
                Arguments.of(8, new int[]{}, "No hay elementos en la colección"),
                Arguments.of(-5, new int[]{1,3,5,7}, "El valor a borrar debe ser > 0"),
                Arguments.of(0, new int[]{}, "Colección vacía. Y el valor a borrar debe ser > 0"),
                Arguments.of(8, new int[]{1,3,5,7}, "Elemento no encontrado")
        );
    }

    private static Stream<Arguments> casosDePruebaC9(){
        return Stream.of(
                Arguments.of(5, new int[]{1,3,5,7}, 3, new int[]{1,3,7}),
                Arguments.of(3, new int[]{1,3,3,5,7}, 4, new int[]{1,3,5,7}),
                Arguments.of(4, new int[]{1,2,3,4,5,6,7,8,9,10}, 9, new int[]{1,2,3,5,6,7,8,9,10})
        );
    }
}