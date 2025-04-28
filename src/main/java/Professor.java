public class Professor {
    private String nome;
    private String matricula; 
    private String departamento;

    public Professor(String nome, String matricula, String departamento) {
        this.nome = nome;
        this.matricula = matricula;
        this.departamento = departamento;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public String toString() {
        return "Professor: " + nome + " | Matrícula: " + matricula + " | Departamento: " + departamento;
    }
}

