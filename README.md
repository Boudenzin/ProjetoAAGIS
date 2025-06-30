# ProjetoAAGIS

[![Licença: MIT](https://img.shields.io/badge/Licen%C3%A7a-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-22%2B-red?logo=openjdk&logoColor=white)](https://www.java.com/)

O **ProjetoAAGIS** é um sistema de gerenciamento de turmas e alunos desenvolvido em Java, inspirado no SIGAA. Ele permite o cadastro de turmas, alunos, e a realização de operações como remoção, pesquisa e listagem de alunos por turma. O sistema também inclui persistência de dados, armazenando as informações em arquivos para recuperação futura.

## Funcionalidades

O projeto oferece as seguintes funcionalidades:

1. **Cadastrar Turma**: Cria uma nova turma com um nome e um docente responsável.
2. **Cadastrar Aluno na Turma**: Adiciona um aluno a uma turma específica, com nome, matrícula e curso.
3. **Ver Todos os Alunos da Turma**: Lista todos os alunos cadastrados em uma turma.
4. **Remover Aluno da Turma**: Remove um aluno de uma turma com base na matrícula.
5. **Remover Turma**: Remove uma turma e todos os alunos associados a ela.


## Tecnologias Utilizadas

- **Linguagem**: Java
- **Interface Gráfica**: `JOptionPane` para interação com o usuário.
- **Persistência de Dados**: Armazenamento em arquivos de texto (`turmas.dat`, `alunos.dat`, `´professores.dat`).
- **Tratamento de Exceções**: Uso de exceções personalizadas para tratamento de erros específicos.

## Estrutura do Projeto

```bash
ProjetoAAGIS/
├───src
│   └───main
│       └───java
│           ├───dao
│           ├───exceptions
│           ├───gui
│           ├───model
│           ├───service
│           └───util
├── README.md                              # Este arquivo.

```
## Como Executar o Projeto

### Pré-requisitos
- Java Development Kit (JDK) instalado.
- Um ambiente de desenvolvimento Java (IDE como IntelliJ, Eclipse, ou VS Code) ou terminal.

---
### Passos para Execução

1. Clone o repositório:
  ```bash
    git clone https://github.com/Boudenzin/ProjetoAAGIS.git
  ```
2. **Navegue até o diretório do projeto**:
  ```bash
    cd ProjetoAAGIS
  ```

3. **Compile o projeto**:
  ```bash
    javac src/main/java/gui/TelaInicial.java
  ```

4. **Execute o programa**:
  ```bash
    java src/main/java/gui/TelaInicial.java
  ```
   
E por final, o sistema exibirá um menu interativo para realizar as operações.

---

## Próximos Objetivos

Aqui listamos futuras metas que queremos implementar nesse projeto:



1. **Validação de Entradas**:
   - Adicionar validações mais robustas para entradas de dados, como matrículas únicas e formatos específicos.

2. **Banco de Dados**:
   - Substituir a persistência em arquivos por um banco de dados (SQLite, MySQL, etc.) para melhor escalabilidade e desempenho.

3. **Relatórios**:
   - Implementar a geração de relatórios em PDF ou Excel com informações das turmas e alunos.

4. **Testes Automatizados**:
   - Implementar testes unitários com JUnit para garantir a qualidade do código.

5. **Documentação de Código**:
   - Adicionar comentários Javadoc para documentar o código de forma profissional.

6. **Deploy**:
   - Publicar o sistema em um servidor ou plataforma na nuvem para acesso remoto.

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
