package ppss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ppss.excepciones.ReservaException;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    final String  validLogin = "ppss";
    final String  validPassword = "ppss";
    final String  validPartner = "Luis";
    final String  invalidPartner = "Pepe";
    final String  validISBNs[] = {"11111", "22222"};
    final Usuario adminUser  = Usuario.BIBLIOTECARIO;

    ReservaTestable resTestable;
    OperacionStub opStub;
    String login, password, socio;

    @BeforeEach
    void inicializacion() {
        opStub = new OperacionStub();
        resTestable = new ReservaTestable();
        resTestable.setLoginValido(validLogin);
        resTestable.setPasswordValido(validPassword);
        resTestable.setTipoValido(adminUser);
    }

    @Test
    void realizaReservaC1() {
        login = "xxxx";
        password = "xxxx";
        socio = validPartner;
        // Cuidado con esto, que no se puede pasar directamente al método SUT, pues se le pasaría un solo String y no en un array.
        String ISBNs[] = {validISBNs[0]};

        OperacionFactory.setOperacion(opStub);

        ReservaException excepcionEsperadda = new ReservaException("ERROR de permisos; ");
        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> resTestable.realizaReserva(login, password, socio, ISBNs));

        assertEquals(excepcionEsperadda.getMessage(), excepcionReal.getMessage());
    }

    @Test
    void realizaReservaC2() {
        login = validLogin;
        password = validPassword;
        socio = validPartner;
        String ISBNs[] = {validISBNs[0], validISBNs[1]};

        OperacionFactory.setOperacion(opStub);

        assertDoesNotThrow(() -> resTestable.realizaReserva(login, password, socio, ISBNs));
    }

    @Test
    void realizaReservaC3() {
        login = validLogin;
        password = validPassword;
        socio = validPartner;
        String ISBNs[] = {"33333"};

        opStub.setThrowISBN(true);
        OperacionFactory.setOperacion(opStub);

        ReservaException excepcionEsperadda = new ReservaException("ISBN invalido:33333; ");
        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> resTestable.realizaReserva(login, password, socio, ISBNs));

        assertEquals(excepcionEsperadda.getMessage(), excepcionReal.getMessage());
    }

    @Test
    void realizaReservaC4() {
        login = validLogin;
        password = validPassword;
        socio = invalidPartner;
        String ISBNs[] = {validISBNs[0]};

        opStub.setThrowSocioInvalido(true);
        OperacionFactory.setOperacion(opStub);

        ReservaException excepcionEsperadda = new ReservaException("SOCIO invalido; ");
        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> resTestable.realizaReserva(login, password, socio, ISBNs));

        assertEquals(excepcionEsperadda.getMessage(), excepcionReal.getMessage());
    }

    @Test
    void realizaReservaC5() {
        login = validLogin;
        password = validPassword;
        socio = validPartner;
        String ISBNs[] = {validISBNs[0]};

        opStub.setThrowJDBC(true);
        OperacionFactory.setOperacion(opStub);

        ReservaException excepcionEsperada = new ReservaException("CONEXION invalida; ");
        ReservaException excepcionReal = assertThrows(ReservaException.class, () -> resTestable.realizaReserva(login, password, socio, ISBNs));

        assertEquals(excepcionEsperada.getMessage(), excepcionReal.getMessage());
    }
}