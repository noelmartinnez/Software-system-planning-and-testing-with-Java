package ppss;

public class Cine {
    public boolean reservaButacasV1(boolean[] asientos, int solicitados)
                                throws ButacasException {
        boolean reserva = false;
        int j = 0;
        int sitiosLibres = 0;
        int primerLibre;

        if (solicitados > asientos.length) {
            throw new ButacasException("No se puede procesar la solicitud");
        }

        while ((j < asientos.length) && (sitiosLibres < solicitados)) {
            if (!asientos[j]) {
                sitiosLibres++;
            } else {
                sitiosLibres = 0;
            }
            j++;
        }
        if ((j >= asientos.length) && (solicitados == 0)){
            reserva = false;
        }
        else {
            if (sitiosLibres == solicitados) {
                primerLibre = (j - solicitados);
                reserva = true;
                for (int k = primerLibre; k < (primerLibre + solicitados); k++) {
                    asientos[k] = true;
                }
            }
        }

        return reserva;
    }
}
