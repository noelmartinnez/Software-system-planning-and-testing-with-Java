package ppss.ejercicio3;

// Subclase creada para poder inicializar la clase AlquilaCoches con un Calendario concreto.

public class AlquilaCochesTestable extends AlquilaCoches {
    // Al ser protected el atributo, puedo usarlo de esta manera en las subclases.
    public void setCalendario(Calendario c) {
        super.calendario = c;
    }
}
