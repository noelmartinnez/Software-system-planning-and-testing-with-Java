package ppss.ejercicio1;

public class GestorLlamadasTestable extends GestorLlamadas {
    // Siempre inicializar cuando haga falta los atributos en las Testables y en los Subs.
    private int hora = 0;

    @Override
    public int getHoraActual(){
        return hora;
    }

    public void setHoraActual(int hora){
        // OJO con el this para indicar que el atirbuto es de esta clase y no de su superclase.
        // Para indicar que es de la superclase se tiene que usar  super.
        this.hora = hora;
    }
}
