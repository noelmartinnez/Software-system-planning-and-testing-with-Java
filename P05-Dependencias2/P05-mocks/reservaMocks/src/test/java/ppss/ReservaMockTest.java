package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

class ReservaMockTest {
    final String loginAndPasswordCorrectos = "ppss";
    final String loginAndPasswordIncorrectos = "xxxx";
    final String usuarioCorrecto = "Pepe";
    final String usuarioIncorrecto = "Luis";
    final String isbnsCorrectos[] = {"22222", "33333"};

    IMocksControl ctrl;
    Reserva reserva;
    FactoriaBOs ft;
    IOperacionBO io;
    ReservaException excepcionEsperada, excepcionReal;

    @BeforeEach
    void inicializacion() {
        ctrl = EasyMock.createStrictControl();
        reserva = EasyMock.partialMockBuilder(Reserva.class).addMockedMethods("compruebaPermisos", "getFactoriaBOs").mock(ctrl);
        ft = ctrl.mock(FactoriaBOs.class);
        io = ctrl.mock(IOperacionBO.class);
    }

    @Test
    void realizaReservaC1() {
        String ISBNs[] = {isbnsCorrectos[0]};
        excepcionEsperada = new ReservaException("ERROR de permisos; ");

        EasyMock.expect(reserva.compruebaPermisos(loginAndPasswordIncorrectos, loginAndPasswordIncorrectos, Usuario.BIBLIOTECARIO)).andReturn(false);
        ctrl.replay();

        excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordIncorrectos, loginAndPasswordIncorrectos, usuarioCorrecto, ISBNs));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
        ctrl.verify();
    }

    @Test
    void realizaReservaC2() {
        // Definimos los comportamientos que no tienen riesgo de saltar excepción.
        EasyMock.expect(reserva.compruebaPermisos(loginAndPasswordCorrectos, loginAndPasswordCorrectos, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(reserva.getFactoriaBOs()).andReturn(ft);
        EasyMock.expect(ft.getOperacionBO()).andReturn(io);

        // Con ".andVoid()" indicamos que no se devuelve ningún valor en la llamada a dicho método.
        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, isbnsCorrectos[0]));
        EasyMock.expectLastCall().andVoid();

        // Cuando haya que poner un orden usar este formato usando .expectLastCall()
        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, isbnsCorrectos[1]));
        EasyMock.expectLastCall().andVoid();

        ctrl.replay();

        assertDoesNotThrow(() -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, isbnsCorrectos));
        ctrl.verify();
    }

    @Test
    void realizaReservaC3() {
        String ISBNs[] = {"11111", "55555"};
        excepcionEsperada = new ReservaException("ISBN invalido:11111; ISBN invalido:55555; ");

        // Cuando el método no tiene posibilidad de lanzar exepción hacerlo así.
        EasyMock.expect(reserva.compruebaPermisos(loginAndPasswordCorrectos, loginAndPasswordCorrectos, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(reserva.getFactoriaBOs()).andReturn(ft);
        EasyMock.expect(ft.getOperacionBO()).andReturn(io);

        // Cuando el método tiene posibilidad de lanzar exepción hacerlo así.
        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, ISBNs[0]));
        EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, ISBNs[1]));
        EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());

        ctrl.replay();

        excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, ISBNs));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
        ctrl.verify();
    }

    @Test
    void realizaReservaC4() {
        String ISBNs[] = {isbnsCorrectos[0]};
        excepcionEsperada = new ReservaException("SOCIO invalido; ");

        EasyMock.expect(reserva.compruebaPermisos(loginAndPasswordCorrectos, loginAndPasswordCorrectos, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(reserva.getFactoriaBOs()).andReturn(ft);
        EasyMock.expect(ft.getOperacionBO()).andReturn(io);

        assertDoesNotThrow(() -> io.operacionReserva(usuarioIncorrecto, ISBNs[0]));
        EasyMock.expectLastCall().andThrow(new SocioInvalidoException());

        ctrl.replay();

        excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioIncorrecto, ISBNs));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
        ctrl.verify();
    }

    @Test
    void realizaReservaC5() {
        String ISBNs[] = {"11111", isbnsCorrectos[0], isbnsCorrectos[1]};
        excepcionEsperada = new ReservaException("ISBN invalido:11111; CONEXION invalida; ");

        EasyMock.expect(reserva.compruebaPermisos(loginAndPasswordCorrectos, loginAndPasswordCorrectos, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(reserva.getFactoriaBOs()).andReturn(ft);
        EasyMock.expect(ft.getOperacionBO()).andReturn(io);

        // Las llamadas se hacen en orden, pues son mocks estrictos. Poner valores concretos.
        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, ISBNs[0]));
        EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, ISBNs[1]));
        EasyMock.expectLastCall().andVoid();

        assertDoesNotThrow(() -> io.operacionReserva(usuarioCorrecto, ISBNs[2]));
        EasyMock.expectLastCall().andThrow(new JDBCException());

        ctrl.replay();

        excepcionReal = assertThrows(ReservaException.class, () -> reserva.realizaReserva(loginAndPasswordCorrectos, loginAndPasswordCorrectos, usuarioCorrecto, ISBNs));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
        ctrl.verify();
    }
}