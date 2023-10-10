package ppss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FicheroTexto {
    // Para controlar la dependencia externa.
    // Esto es usar una factoría local. Luego con el partialMockBuilder podemos controlar lo que devuelve.
    public FileReader getFileReader(String nombreFichero) throws FileNotFoundException {
        return new FileReader(nombreFichero);
    }
    public int contarCaracteres(String nombreFichero) throws FicheroException {
        int contador = 0;
        FileReader fichero = null;
        try {
            fichero = getFileReader(nombreFichero);
            int i=0;
            while (i != -1) {
                i = fichero.read();
                contador++;
            }
            contador--;
        } catch (FileNotFoundException e1) {
            throw new FicheroException(nombreFichero +
                    " (No existe el archivo o el directorio)");
        } catch (IOException e2) {
            throw new FicheroException(nombreFichero +
                    " (Error al leer el archivo)");
        }
        try {
            System.out.println("Antes de cerrar el fichero");
            fichero.close();
        } catch (IOException e) {
            throw new FicheroException(nombreFichero +
                    " (Error al cerrar el archivo)");
        }
        return contador;
    }
}
