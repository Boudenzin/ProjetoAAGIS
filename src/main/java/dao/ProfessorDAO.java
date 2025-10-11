package dao;

import model.Professor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    private static final String ARQUIVO = "professores.dat";
    private List<Professor> professores;
    private GravadorDeDados<Professor> gravador;

    public ProfessorDAO() {
        this.gravador = new GravadorDeDados<>();
        try {
            this.professores = gravador.recuperar(ARQUIVO);
        } catch (Exception e) {
            this.professores = new ArrayList<>();
        }
    }

    public void salvar(Professor professor) throws IOException {
        professores.add(professor);
        gravador.gravar(professores, ARQUIVO);
    }

    public Professor buscarPorUsuario(String usuario) {

        if (usuario == null) {
            return null;
        }

        return professores.stream()
                .filter(p -> p.getUsuario().equalsIgnoreCase(usuario))
                .findFirst()
                .orElse(null);
    }

    public List<Professor> listarTodos() {
        return professores;
    }
}
