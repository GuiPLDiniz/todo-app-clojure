
# Todo List — Clojure e ClojureScript

## 1. Sobre o projeto

Aplicação web de gerenciamento de tarefas, desenvolvida
como trabalho da disciplina de Programação Funcional.

O projeto utiliza Clojure no backend, ClojureScript no
frontend e SQLite para persistência dos dados.

## 2. Autor

- Nome: Guilherme Pessoa Lima Diniz
- Disciplina: Programação Funcional
- Repositório: https://github.com/GuiPLDiniz/todo-app-clojure

## 3. Funcionalidades

- Criar novas tarefas.
- Listar tarefas cadastradas.
- Marcar tarefas como concluídas.
- Desmarcar tarefas concluídas.
- Excluir tarefas.
- Manter os dados após reiniciar o servidor.

## 4. Tecnologias utilizadas

### Backend
- Clojure
- Ring e Jetty
- Reitit
- Muuntaja
- next.jdbc
- SQLite

### Frontend
- ClojureScript
- Reagent
- React
- Shadow-CLJS

### Ferramentas
- Java JDK
- Node.js e npm
- Git e GitHub
- Visual Studio Code

## 5. Arquitetura

A aplicação está organizada em três camadas:

1. Frontend: interface gráfica com Reagent.
2. Backend: API REST implementada com Ring e Reitit.
3. Persistência: banco SQLite acessado com next.jdbc.

O frontend se comunica com o backend por requisições
HTTP, utilizando JSON.

### Endpoints da API

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | /api/todos | Lista tarefas |
| POST | /api/todos | Cria tarefa |
| POST | /api/todos/:id/toggle | Alterna conclusão |
| DELETE | /api/todos/:id | Exclui tarefa |

## 6. Pré-requisitos

Para executar o projeto, são necessários:

- Java JDK 11 ou superior.
- Clojure CLI 1.11 ou superior.
- Node.js e npm.
- Git (para clonar o repositório).

## 7. Instalação

Clonar o repositório:

```bash
git clone https://github.com/GuiPLDiniz/todo-app-clojure.git
```

Entrar na pasta do projeto:

```bash
cd todo-app-clojure
```

Instalar as dependências JavaScript:

```bash
npm install
```

## 8. Executar a aplicação

### Backend

No primeiro terminal:

```bash
clojure -M:run
```

O backend ficará disponível em:

http://localhost:3000

O banco SQLite e sua tabela serão criados
automaticamente, caso ainda não existam.

### Frontend

Em um segundo terminal:

```bash
npx shadow-cljs watch app
```

Aguardar a mensagem "Build completed".

Abrir no navegador:

http://localhost:8000

## 9. Persistência dos dados

As tarefas são armazenadas no arquivo `prod.db`,
gerenciado pelo SQLite.

O arquivo é criado automaticamente durante a execução
e não está incluído no repositório Git.

As tarefas continuam armazenadas após o reinício
do backend.

## 10. Conceitos de Programação Funcional

O frontend utiliza estruturas de dados imutáveis
e estado reativo com Reagent.

As funções de interface são definidas em
ClojureScript, usando a sintaxe Hiccup.

O backend utiliza funções para organizar as
operações HTTP e o acesso ao banco de dados.

As alterações persistentes são realizadas
por funções específicas da camada de dados.

## 11. Tutorial de referência

https://lambdageo-edu.github.io/lambdageo-tutorials/clojure/

## 12. Vídeo de apresentação

[INSERIR LINK DO VÍDEO APÓS A GRAVAÇÃO]

O vídeo apresenta a arquitetura, as funcionalidades,
a persistência SQLite e as principais dificuldades
enfrentadas durante o desenvolvimento.
