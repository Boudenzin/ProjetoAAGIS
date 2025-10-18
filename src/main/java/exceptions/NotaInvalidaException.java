package exceptions;

import java.io.Serial;

public class NotaInvalidaException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotaInvalidaException(String message) {
        super(message);
    }
}
