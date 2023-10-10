package ppss.ejercicio2;

public class CalendarioStub extends Calendario {
    private int hora = 0;

    public void setHoraActual(int h) {
        hora = h;
    }

    @Override
    public int getHoraActual() {
        return hora;
    }
}
