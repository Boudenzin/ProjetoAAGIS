public interface AlunoInterface {
    public boolean cadastrarAluno(Aluno s);
    public boolean cadastrarCadeira(Cadeira cadeira);
    public Aluno pesquisarAluno(String matricula);
    public boolean listarAlunosDoCurso(String curso);
    public double calcularCra(String matricula, Cadeira cadeira);

}
