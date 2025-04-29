package components;

import java.io.IOException;
import java.util.List;
public interface GravadorInterface<T> {
    void gravar(List<T> lista, String arquivo) throws IOException;
    List<T> recuperar(String arquivo) throws IOException, ClassNotFoundException;
}
