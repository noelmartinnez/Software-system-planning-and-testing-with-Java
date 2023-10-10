package ppss.ejercicio3;

import java.time.LocalDate;

public class CalendarioStub extends Calendario{
    private int festivos[] = {};
    private int excepciones[] = {};

    public void setFestivos(int f[]) {
        this.festivos = f;
    }

    public void setExcepciones(int e[]) {
        this.excepciones = e;
    }

    @Override
    public boolean es_festivo(LocalDate date) throws CalendarioException {
        for (int i = 0; i < excepciones.length ;i++) {
            if (excepciones[i] == date.getDayOfMonth()) {
                throw new CalendarioException(date.toString());
            }
        }

        for (int i = 0; i < festivos.length ;i++) {
            if (festivos[i] == date.getDayOfMonth()) {
                return true;
            }
        }

        return false;
    }
}
