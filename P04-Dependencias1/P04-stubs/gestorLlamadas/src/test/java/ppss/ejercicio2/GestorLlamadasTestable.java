package ppss.ejercicio2;

// Controlamos que objeto de Calendario se va a devolver.

public class GestorLlamadasTestable extends GestorLlamadas{
    private Calendario c;

    public void setCalendario(Calendario c) {
        this.c = c;
    }

    @Override
    public Calendario getCalendario(){
        return c;
    }
}
