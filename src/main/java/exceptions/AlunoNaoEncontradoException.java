package exceptions;

import java.io.Serial;

public class AlunoNaoEncontradoException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public AlunoNaoEncontradoException(String e) {
        super(e);
    }
}
