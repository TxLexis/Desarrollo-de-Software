package src.main.java.com.reversedots.exception;

public class RepositoryException extends GameException {
    private static final long serialVersionUID = 1L;

    public RepositoryException(String mensaje) {
        super(mensaje);
    }

    public RepositoryException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
