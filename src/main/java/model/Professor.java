package model;

import java.io.Serializable;
import java.util.Objects;

public class Professor implements Serializable {
    private String nome;
    private String matricula;
    private String departamento;
    private String usuario;
    private String senha;

    public Professor(String nome, String matricula, String departamento, String usuario, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.departamento = departamento;
        this.usuario = usuario;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor that = (Professor) o;
        return Objects.equals(matricula, that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}
