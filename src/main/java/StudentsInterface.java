public interface StudentsInterface {
    public boolean cadastrarAluno(Students s);
    public boolean cadastrarCadeira();
    public Students pesquisarAluno(String matricula);
    public boolean listarAlunosDoCurso(String curso);
    public double calcularCra(String matricula, Cadeira cadeira);

}
