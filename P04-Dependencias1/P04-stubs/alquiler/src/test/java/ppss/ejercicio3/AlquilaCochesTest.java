package ppss.ejercicio3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {
    AlquilaCochesTestable ac;
    CalendarioStub cal;
    ServicioStub ser;
    TipoCoche tipo;
    int dias;
    float precioEsperado;
    LocalDate fecha;
    String errorEsperado;
    MensajeException errorReal;
    Ticket esperado, real;

    @BeforeEach
    void inicializacion() {
        ac = new AlquilaCochesTestable();
        cal = new CalendarioStub();
        esperado = new Ticket();
        ser = new ServicioStub();

        ser.setPrecio(10);
        // OJO
        ServicioFactory.setServicio(ser);
    }

    @Test
    void calculaPrecioC1() {
        // Importante saber como usar un enurmerado.
        tipo = TipoCoche.TURISMO;
        fecha = LocalDate.of(2021, Month.MAY, 18);
        dias = 10;
        precioEsperado = 75;

        int diasFestivos[] = {};

        cal.setFestivos(diasFestivos);
        ac.setCalendario(cal);
        esperado.setPrecio_final(precioEsperado);

        real = assertDoesNotThrow(() -> ac.calculaPrecio(tipo, fecha, dias));
        assertEquals(esperado.getPrecio_final(), real.getPrecio_final());
    }

    @Test
    void calculaPrecioC2() {
        tipo = TipoCoche.CARAVANA;
        fecha = LocalDate.of(2021, Month.JUNE, 19);
        dias = 7;
        precioEsperado = 62.5f;

        int diasFestivos[] = {20, 24};

        cal.setFestivos(diasFestivos);
        ac.setCalendario(cal);
        esperado.setPrecio_final(precioEsperado);

        real = assertDoesNotThrow(() -> ac.calculaPrecio(tipo, fecha, dias));
        assertEquals(esperado.getPrecio_final(), real.getPrecio_final());
    }

    @Test
    void calculaPrecioC3() {
        tipo = TipoCoche.TURISMO;
        fecha = LocalDate.of(2021, Month.APRIL, 17);
        dias = 8;
        errorEsperado = "Error en dia: 2021-04-18; Error en dia: 2021-04-21; Error en dia: 2021-04-22; ";

        int diasExcepcion[] = {18, 21, 22};

        cal.setExcepciones(diasExcepcion);
        ac.setCalendario(cal);

        errorReal = assertThrows(MensajeException.class, () -> ac.calculaPrecio(tipo, fecha, dias));
        assertEquals(errorEsperado, errorReal.getMessage());
    }
}