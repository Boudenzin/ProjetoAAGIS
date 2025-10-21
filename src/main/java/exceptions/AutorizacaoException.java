package exceptions;

import java.io.Serial;

public class AutorizacaoException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public AutorizacaoException(String message) {
        super(message);
    }
}
