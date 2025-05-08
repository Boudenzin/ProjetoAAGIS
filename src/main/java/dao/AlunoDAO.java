package dao;

import model.Aluno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private static final String ARQUIVO = "alunos.dat";
    private List<Aluno> alunos;
    private GravadorDeDados<Aluno> gravador;

    public AlunoDAO() {
        this.gravador = new GravadorDeDados<>();
        try {
            this.alunos = gravador.recuperar(ARQUIVO);
        } catch (Exception e) {
            this.alunos = new ArrayList<>();
        }
    }

    public void salvar(Aluno aluno) throws IOException {
        alunos.add(aluno);
        gravador.gravar(alunos, ARQUIVO);
    }

    public Aluno buscarPorUsuario(String usuario) {
        return alunos.stream()
                .filter(a -> a.getUsuario().equals(usuario))
                .findFirst()
                .orElse(null);
    }
    public Aluno buscarPorMatricula(String matricula) {
        return alunos.stream()
                .filter(a -> a.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

    public List<Aluno> listarTodos() {
        return alunos;
    }
}


