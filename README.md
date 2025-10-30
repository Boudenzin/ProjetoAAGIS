# ProjetoAAGIS

[![Licença: MIT](https://img.shields.io/badge/Licen%C3%A7a-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-22%2B-red?logo=openjdk\&logoColor=white)](https://www.java.com/)

O **ProjetoAAGIS** é um sistema de gerenciamento de turmas e alunos desenvolvido em **Java**, inspirado no SIGAA. Ele oferece uma interface gráfica construída com `javax.swing`, permitindo uma experiência mais interativa para professores e alunos. O sistema conta com autenticação, persistência de dados via arquivos e uma série de funcionalidades para o gerenciamento acadêmico.

## Funcionalidades

O sistema é dividido em duas áreas principais: **Área do Professor** e **Área do Aluno**, além da **tela de autenticação e cadastro** para ambos os perfis.

### Área do Aluno

* Consultar turmas cadastradas
* Ver notas por disciplina
* Ver faltas por disciplina

### Área do Professor

* Cadastrar novas turmas
* Consultar turmas existentes
* Cadastrar alunos em uma turma
* Listar alunos da turma
* Adicionar notas para alunos
* Adicionar faltas para alunos

### Funcionalidades Gerais

* Cadastro e autenticação de alunos e professores
* Armazenamento persistente das informações (em arquivos)
* Interface gráfica com `javax.swing` (`JFrame`, `JPanel`, `JButton`, etc.)

## Tecnologias Utilizadas

* **Linguagem**: Java
* **Interface Gráfica**: `javax.swing`
* **Persistência de Dados**: Arquivos (`.dat`)
* **Tratamento de Exceções**: Uso de exceções personalizadas

## Estrutura do Projeto

```
ProjetoAAGIS/
├───src
│   └───main
│       └───java
│           ├───dao            # Lógica de acesso a dados
│           ├───exceptions     # Exceções personalizadas
│           ├───gui            # Telas e menus da interface gráfica
│           ├───model          # Entidades: Turma, Aluno, Professor etc.
│           ├───service        # Regras de negócio
│           └───util           # Utilitários diversos
├───README.md
```

## Como Executar o Projeto

### Pré-requisitos

* JDK 21
* IDE Java (IntelliJ, Eclipse, VS Code) ou terminal com Java configurado

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/Boudenzin/ProjetoAAGIS.git
```

2. Acesse o diretório do projeto:

```bash
cd ProjetoAAGIS
```

3. Compile a classe principal:

```bash
javac src/main/java/gui/TelaInicial.java
```

4. Execute o programa:

```bash
java -cp src/main/java gui.TelaInicial
```


## Próximos Objetivos

* [ ] Validação de entradas (ex: impedir matrícula duplicada)
* [ ] Adicionar remoção de turmas por parte do professor
* [ ] Integração com banco de dados (ex: SQLite ou MySQL)
* [ ] Geração de relatórios (PDF ou Excel)
* [ ] Adição de documentação Javadoc
* [ ] Disponibilizar o sistema online via deploy em nuvem

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
