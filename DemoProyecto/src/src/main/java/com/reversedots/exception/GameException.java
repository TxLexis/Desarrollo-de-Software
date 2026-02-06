package src.main.java.com.reversedots.exception;

public class GameException extends Exception {
    private static final long serialVersionUID = 1L;

    public GameException(String mensaje) {
        super(mensaje);
    }

    public GameException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
