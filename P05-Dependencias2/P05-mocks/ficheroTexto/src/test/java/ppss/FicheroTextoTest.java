package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    final String fichero1 = "src/test/resources/ficheroC1.txt";
    final String fichero2 = "src/test/resources/ficheroC2.txt";

    FicheroTexto ft;
    FileReader fr;
    FicheroException excepcionEsperada, excepcionReal;
    IMocksControl ctrl;

    @BeforeEach
    void inicializacion() {
        ctrl = EasyMock.createStrictControl();
        // Cuando tenemos que usar el IMocksControl, creamos los mocks en vez de con .strictMock() con .mock(), pero se crearán como strictMocks
        ft = EasyMock.partialMockBuilder(FicheroTexto.class).addMockedMethod("getFileReader").mock(ctrl);
        fr = ctrl.mock(FileReader.class);
    }

    @Test
    void contarCaracteresC1() {
        excepcionEsperada = new FicheroException("src/test/resources/ficheroC1.txt (Error al leer el archivo)");

        assertDoesNotThrow(() -> ft.getFileReader(fichero1));
        EasyMock.expectLastCall().andReturn(fr);

        // Aquí es donde se especifica el orden de las llamadas al método de la dependencia externa y lo que tiene que devolver en cada una.
        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andReturn(Integer.valueOf('a'));

        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andReturn(Integer.valueOf('b'));

        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andThrow(new IOException());

        ctrl.replay();

        excepcionReal = assertThrows(FicheroException.class, () -> ft.contarCaracteres(fichero1));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
        ctrl.verify();
    }

    @Test
    void contarCaracteresC2() {
        excepcionEsperada = new FicheroException("src/test/resources/ficheroC2.txt (Error al cerrar el archivo)");

        assertDoesNotThrow(() -> EasyMock.expect(ft.getFileReader(fichero2)).andReturn(fr));

        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andReturn(Integer.valueOf('a'));

        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andReturn(Integer.valueOf('b'));

        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andReturn(Integer.valueOf('c'));

        assertDoesNotThrow(() -> fr.read());
        EasyMock.expectLastCall().andReturn(-1);

        assertDoesNotThrow(() -> fr.close());
        EasyMock.expectLastCall().andThrow(new IOException());

        ctrl.replay();

        excepcionReal = assertThrows(FicheroException.class, () -> ft.contarCaracteres(fichero2));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
        ctrl.verify();
    }
}