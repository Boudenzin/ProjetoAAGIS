package exceptions;

import java.io.Serial;

public class TurmaJaCriadaException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public TurmaJaCriadaException(String message) {
        super(message);
    }
}
