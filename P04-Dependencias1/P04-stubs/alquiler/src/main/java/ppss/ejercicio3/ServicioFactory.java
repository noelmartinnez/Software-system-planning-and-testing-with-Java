package ppss.ejercicio3;

//
// Clase necesaria para refactorizar el método, pues con "new Servicio()" no podremos inyectar un DOBLE. AL crear esta clase, podemos usar su método create()
// que devuelve "new Servicio()" en producción; podemos cambiar lo que devuelve en los test, de esta manera podremos inyectar el DOBLE ServicioStub.
//

public class ServicioFactory {
    private static IService servicio = null;
    public static IService create() {
        if( servicio != null ) {
            return servicio;
        } else {
            return new Servicio();
        }
    }

    public static void setServicio(IService s) {
        servicio = s;
    }
}
