package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FicheroTextoTest {
    final String ficheroCorrecto = "src/test/resources/ficheroCorrecto.txt";
    final String ficheroIncorrecto = "ficheroC1.txt";
    int Real, Esperado;
    FicheroTexto f;

    @BeforeEach
    public void inicializacion() {
        f = new FicheroTexto();
    }

    @Test
    public void contarCaracteresC1() {
        FicheroException ex = assertThrows(FicheroException.class, () -> f.contarCaracteres(ficheroIncorrecto));
        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", ex.getMessage());
    }

    //El código está mal implementado, pues el "-1" que indica que se termina el fichero, también es contado.
    //No cambio el código porque no lo pide el ejercicio.
    @Test
    public void contarCaracteresC2() {
        Esperado = 3;

        Real = assertDoesNotThrow(() -> f.contarCaracteres(ficheroCorrecto));
        assertEquals(Esperado,Real);
    }
}