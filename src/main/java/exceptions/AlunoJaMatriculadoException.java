package exceptions;

import java.io.Serial;

public class AlunoJaMatriculadoException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public AlunoJaMatriculadoException(String mensagem) {
        super(mensagem);
    }
}
