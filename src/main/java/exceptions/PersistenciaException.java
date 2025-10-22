package exceptions;

import java.io.IOException;
import java.io.Serial;

public class PersistenciaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PersistenciaException(String message, IOException e) {
        super(message);
    }
}
