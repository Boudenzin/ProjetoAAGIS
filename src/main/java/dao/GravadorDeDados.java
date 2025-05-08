package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GravadorDeDados<T> implements GravadorInterface<T> {

    @Override
    public void gravar(List<T> lista, String arquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(lista);
        }
    }

    @Override
    public List<T> recuperar(String arquivo) throws IOException, ClassNotFoundException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return new ArrayList<>(); // Retorna lista vazia se arquivo não existir ainda
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) in.readObject();
        }
    }
}