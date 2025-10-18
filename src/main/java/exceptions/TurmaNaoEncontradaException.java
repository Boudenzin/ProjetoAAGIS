package exceptions;

import java.io.Serial;

public class TurmaNaoEncontradaException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public TurmaNaoEncontradaException(String e) {
        super(e);
    }
}
