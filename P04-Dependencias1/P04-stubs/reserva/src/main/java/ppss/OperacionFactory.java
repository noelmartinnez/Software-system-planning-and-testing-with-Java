package ppss;

// Cuidado con las clases factoría, porque los objetos static no se puede referenciar con "this."

public class OperacionFactory {
    private static IOperacionBO operacion = null;
    public static IOperacionBO create() {
        if( operacion != null ) {
            return operacion;
        } else {
            return new Operacion();
        }
    }

    public static void setOperacion(IOperacionBO o) {
        operacion = o;
    }
}
