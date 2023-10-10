package ppss.ejercicio3;

public class ServicioStub implements IService{
    private float precio = 0.0f;
    @Override
    public float consultaPrecio(TipoCoche tipo) {
        return precio;
    }

    public void setPrecio(float p) {
        precio = p;
    }
}
