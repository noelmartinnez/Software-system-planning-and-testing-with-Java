package ppss;

import org.easymock.EasyMock;
import org.junit.jupiter.api.*;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaStubTest {
    final String loginAndPasswordCorrectos = "ppss";
    final String loginAndPasswordIncorrectos = "xxxx";
    final String usuarioCorrecto = "Pepe";
    final String isbnsCorrectos[] = {"22222", "33333"};

    Reserva reserva;
    FactoriaBOs ft;
    IOperacionBO io;
    String excepcionEsperada;

    @BeforeEach
    void inicializacion() {
        reserva = EasyMock.partialMockBuilder(Reserva.class).addMockedMethods("compruebaPermisos", "getFactoriaBOs").niceMock();
        ft = EasyMock.niceMock(FactoriaBOs.class);
        io = EasyMock.niceMock(IOperacionBO.class);

        EasyMock.expect(reserva.getFactoriaBOs()).andStubReturn(ft);
        EasyMock.expect(ft.getOperacionBO()).andStubReturn(io);
    }

    @Test
    void realizaReservaC1() {
        String[] ISBNs = {isbnsCorrectos[0]};
        excepcionEsperada = "ERROR de permisos; ";

        // Aquí se cambia el comportamiento del método .compruebaPermisos() y le decimos que devuelva si o si false.
        // Entonces al ejecutar la llamada a .realizaReserva(), este método tiene que dar error si o si.
        EasyMock.expect(reserva.compruebaPermisos(anyString(), anyString(), anyObject())).andStubReturn(false);

        // No se hace el replay de las otras clases, porque no se llegan a ejecutar.
        EasyMock.replay(reserva);

        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordIncorrectos, loginAndPasswordIncorrectos, usuarioCorrecto, ISBNs));
        assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void realizaReservaC2() {
        EasyMock.expect(reserva.compruebaPermisos(anyString(), anyString(), anyObject())).andStubReturn(true);
        EasyMock.replay(reserva, ft, io);
        assertDoesNotThrow(() -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, isbnsCorrectos));
    }

    @Test
    void realizaReservaC3() {
        String[] ISBNs = {"11111", "55555"};
        excepcionEsperada = "ISBN invalido:11111; ISBN invalido:55555; ";

        EasyMock.expect(reserva.compruebaPermisos(anyString(), anyString(), anyObject())).andStubReturn(true);

        assertDoesNotThrow(() -> io.operacionReserva(anyString(), anyString()));
        EasyMock.expectLastCall().andStubThrow(new IsbnInvalidoException());

        EasyMock.replay(reserva, ft, io);

        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, ISBNs));
        assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void realizaReservaC4() {
        String[] ISBNs = {isbnsCorrectos[0]};
        excepcionEsperada = "SOCIO invalido; ";

        EasyMock.expect(reserva.compruebaPermisos(anyString(), anyString(), anyObject())).andStubReturn(true);

        assertDoesNotThrow(() -> io.operacionReserva(anyString(), anyString()));
        EasyMock.expectLastCall().andStubThrow(new SocioInvalidoException());

        EasyMock.replay(reserva, ft, io);

        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, ISBNs));
        assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void realizaReservaC5() {
        String[] ISBNs = {"11111", isbnsCorrectos[0], isbnsCorrectos[1]};
        excepcionEsperada = "ISBN invalido:11111; CONEXION invalida; ";

        EasyMock.expect(reserva.compruebaPermisos(anyString(), anyString(), anyObject())).andStubReturn(true);

        // El ".eq()" es el equals de EasyMock.
        assertDoesNotThrow(() -> io.operacionReserva(anyString(), EasyMock.eq("11111")));
        EasyMock.expectLastCall().andStubThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> io.operacionReserva(anyString(), EasyMock.eq(isbnsCorrectos[0])));
        EasyMock.expectLastCall().andVoid();

        assertDoesNotThrow(() -> io.operacionReserva(anyString(), EasyMock.eq(isbnsCorrectos[1])));
        EasyMock.expectLastCall().andStubThrow(new JDBCException());

        EasyMock.replay(reserva, ft, io);

        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, ISBNs));
        assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }
}