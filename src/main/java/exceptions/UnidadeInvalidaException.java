package exceptions;

import java.io.Serial;

public class UnidadeInvalidaException extends Exception {

    @Serial
    public static final long serialVersionUID = 1L;

    public UnidadeInvalidaException(String message) {
        super(message);
    }
}
