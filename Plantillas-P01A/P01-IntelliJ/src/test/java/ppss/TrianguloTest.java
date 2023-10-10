package ppss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrianguloTest {
    int a,b,c;
    String resultadoReal, resultadoEsperado;
    Triangulo tri;

    @Test
    public void testTipo_trianguloC1() {
       a = 1;
       b = 1;
       c = 1;
       resultadoEsperado = "Equilatero";
       tri= new Triangulo();
       resultadoReal = tri.tipo_triangulo(a,b,c);
       assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testTipo_trianguloC2() {
        a = 1;
        b = 1;
        c = 11;
        resultadoEsperado = "No es un triangulo";
        tri= new Triangulo();
        resultadoReal = tri.tipo_triangulo(a,b,c);
        //El método Assert.assertEquals devuelve cierto si el resultadoEsperado = resultadoReal
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testTipo_trianguloC3() {
        a = 1;
        b = 2;
        c = 0;
        resultadoEsperado = "Valor c sobrepasa el rango permitido";
        tri= new Triangulo();
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }


    @Test
    public void testTipo_trianguloC4() {
        a = 14;
        b = 10;
        c = 10;
        resultadoEsperado = "Isosceles";
        tri= new Triangulo();
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testTipo_trianguloC5() {
        a = 14;
        b = 10;
        c = 9;
        resultadoEsperado = "Escaleno";
        tri= new Triangulo();
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void testTipo_trianguloC6() {
        a = 20;
        b = 10;
        c = 10;
        resultadoEsperado = "No es un triangulo";
        tri= new Triangulo();
        resultadoReal = tri.tipo_triangulo(a,b,c);
        assertEquals(resultadoEsperado, resultadoReal);
    }

}
