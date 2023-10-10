package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

public class OperacionStub implements IOperacionBO{
    private boolean throwISBN = false;
    private boolean throwJDBC = false;
    private boolean throwSocioInvalido = false;

    public void setThrowISBN(boolean throwISBN) { this.throwISBN = throwISBN; }

    public void setThrowJDBC(boolean throwJDBC) {
        this.throwJDBC = throwJDBC;
    }

    public void setThrowSocioInvalido(boolean throwSocioInvalido) {
        this.throwSocioInvalido = throwSocioInvalido;
    }

    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        if(throwISBN) throw new IsbnInvalidoException();
        if(throwJDBC) throw new JDBCException();
        if(throwSocioInvalido) throw new SocioInvalidoException();
    }
}
