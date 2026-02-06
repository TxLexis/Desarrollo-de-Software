package src.main.java.com.reversedots.exception;

public class MovimientoInvalidoException extends GameException {
    private static final long serialVersionUID = 1L;

    public MovimientoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public MovimientoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
