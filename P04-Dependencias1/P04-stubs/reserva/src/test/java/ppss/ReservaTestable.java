package ppss;

public class ReservaTestable extends Reserva {
    private String loginValido;
    private String passwordValido;
    private Usuario tipoValido;

    public void setLoginValido(String login) {
        loginValido = login;
    }

    public void setPasswordValido(String password) {
        passwordValido = password;
    }

    public void setTipoValido(Usuario tipo) {
        tipoValido = tipo;
    }

    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        return ((login == loginValido) && (password == passwordValido) && (tipoUsu == tipoValido));
    }
}
