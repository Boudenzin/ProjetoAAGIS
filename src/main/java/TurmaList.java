import java.util.List;
public class TurmaList implements TurmaInterface {

    private List<Turma> turmas;

    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException {
        if (turmas.contains(turma)) {
            throw new TurmaJaCriadaException("Turma já cadastrada no sistema com essas credenciais");
        }

        turmas.add(turma);
    }

    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) {
        // Valida dados de entrada (nome do aluno, nome da turma)
        // Cria uma nova turma com o nome informado
        // Adiciona o aluno à nova turma
        for (Turma t : turmas) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                t.addAluno(aluno);
            }
        }
    }


/*

    //public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) {
        // Valida dados de entrada (nome da turma, matrícula)
        // Busca a turma pelo nome
        // Busca o aluno na turma pela matrícula
        // Retorna o objeto Aluno se encontrado, null caso contrário
    }

    public boolean verificarExistenciaAluno(String matricula, String nomeTurma) {
        // Valida dados de entrada (nome da turma, matrícula)
        // Busca a turma pelo nome
        // Verifica se o aluno já está na turma
        // Retorna true se o aluno estiver na turma, false caso contrário
    }

    public List<Aluno> listarAlunosDaTurma(String nomeTurma) {
        // Valida dados de entrada (nome da turma)
        // Busca a turma pelo nome
        // Retorna a lista de alunos da turma, lista vazia caso não existam alunos
    }

     */
}