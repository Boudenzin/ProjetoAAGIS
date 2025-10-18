package exceptions;

import java.io.Serial;

public class CredenciaisInvalidasException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
