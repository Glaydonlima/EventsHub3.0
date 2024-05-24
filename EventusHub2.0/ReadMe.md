<h1 align="center" style="font-weight: bold;">Event's Hub</h1>

<h2 align="center" style="font-weight: bold;">Principais Tecnologias</h2>

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-%236DB33F.svg?style=for-the-badge&logo=spring-security&logoColor=white)

# Definições da API Event's Hub

## Informações da API
- Title: API REST para Gerenciamento de Usuários, Prestadores e Serviços da plataforma Event's Hub.
- Version: v0.1

## Server
- URL: http://localhost:8080

## :pushpin:  Sobre o Projeto

A API Event's Hub foi criada como projeto final do curso Start - Rede Cidadã em parceria com a Accenture Brasil.

## Funcionalidades
- [Autenticação](#1-autenticacao)
- [Gerenciamento de Usuários](#2-gerenciamento-de-usuarios)
- [Gerenciamento de Prestadores](#3-gerenciamento-de-prestadores)
- [Gerenciamento de Clientes](#4-gerenciamento-de-clientes)
- [Gerenciamento de Serviços](#5-gerenciamento-de-servicos)

## Arquitetura

Este projeto segue intenções de uma arquitetura inspirada no padrão [MVC](https://blog.ironlinux.com.br/entendendo-mvc/). Ele é 
dividido em camadas para separar as responsabilidades e tornar o código mais organizado e testável.

## Endpoints

### 1. Autenticação
#### 1.1 Login de Usuário
- **URL**: /usuario/login
- **Método**: POST
- **Tags**: usuario-controle
- **Descrição**: Realiza o login de um usuário.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**:
    ```json
    {
      "email": "string",
      "senha": "string"
    }
    ```
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Login bem-sucedido.
  - 400 Bad Request: Requisição inválida.

#### 1.2 Cadastrar Usuário
- **URL**: /usuario/cadastrar
- **Método**: POST
- **Tags**: usuario-controle
- **Descrição**: Cadastra um novo usuário.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**:
    ```json
    {
      "nome": "string",
      "email": "string",
      "senha": "string"
    }
    ```
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Cadastro bem-sucedido.
  - 400 Bad Request: Requisição inválida.
  - 403 Unauthorized: Acesso negado.

### 2. Gerenciamento de Usuários
#### 2.1 Listar Usuários
- **URL**: /usuario/listar
- **Método**: GET
- **Tags**: usuario-controle
- **Descrição**: Lista todos os usuários.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 400 Bad Request: Requisição inválida.
  - 403 Unauthorized: Acesso negado.

#### 2.2 Alterar Usuário
- **URL**: /usuario/alterar/{id}
- **Método**: PUT
- **Tags**: usuario-controle
- **Descrição**: Altera os dados de um usuário existente.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do usuário a ser alterado.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**:
    ```json
    {
      "nome": "string",
      "email": "string",
      "senha": "string"
    }
    ```
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 400 Bad Request: Requisição inválida.
  - 403 Unauthorized: Acesso negado.

#### 2.3 Remover Usuário
- **URL**: /usuario/remover/{id}
- **Método**: DELETE
- **Tags**: usuario-controle
- **Descrição**: Remove um usuário específico.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do usuário.
- **Respostas**
  - 200 OK: Usuário removido com sucesso.
  - 400 Bad Request: Requisição inválida.
  - 403 Unauthorized: Acesso negado.

### 3. Gerenciamento de Prestadores

#### 3.1 Cadastrar Prestador
- **URL**: /prestador/cadastrar/{id}
- **Método**: POST
- **Tags**: prestador-controle
- **Descrição**: Cadastre um novo prestador.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do prestador.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**: PrestadorModelo
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Cadastro bem-sucedido.

#### 3.2 Listar Prestadores
- **URL**: /prestador/listar
- **Método**: GET
- **Tags**: prestador-controle
- **Descrição**: Lista todos os prestadores.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 3.3 Obter Prestador por ID
- **URL**: /prestador/{id}
- **Método**: GET
- **Tags**: prestador-controle
- **Descrição**: Obtém um prestador específico pelo ID.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do prestador.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 3.4 Alterar Prestador
- **URL**: /prestador/alterar/{id}
- **Método**: PUT
- **Tags**: prestador-controle
- **Descrição**: Altera os dados de um prestador existente.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do prestador a ser alterado.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**:
    ```json
    {
      "razaoSocial": "string",
      "cpf": "string",
      "descricaoEmpresa": "string",
      "portfolio": "string",
      "usuario": {
        "nome": "string",
        "email": "string",
        "senha": "string"
      }
    }
    ```
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 400 Bad Request: Requisição inválida.
  - 403 Unauthorized: Acesso negado.

#### 3.5 Remover Prestador
- **URL**: /prestador/remover/{id}
- **Método**: DELETE
- **Tags**: prestador-controle
- **Descrição**: Remove um prestador específico.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do prestador.
- **Respostas**
  - 200 OK: Prestador removido com sucesso.
  - 403 Unauthorized: Acesso negado.

### 4. Gerenciamento de Clientes
#### 4.1 Cadastrar Cliente
- **URL**: /clientes/cadastrar/{id}
- **Método**: POST
- **Tags**: cliente-controle
- **Descrição**: Cadastra um novo cliente.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do cliente.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**: ClienteModelo
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Cadastro bem-sucedido.

#### 4.2 Listar Clientes
- **URL**: /clientes/listar
- **Método**: GET
- **Tags**: cliente-controle
- **Descrição**: Lista todos os clientes.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 4.3 Obter Cliente por ID
- **URL**: /clientes/{id}
- **Método**: GET
- **Tags**: cliente-controle
- **Descrição**: Obtém um cliente específico pelo ID.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do cliente.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 4.4 Alterar Cliente
- **URL**: /clientes/alterar/{id}
- **Método**: PUT
- **Tags**: cliente-controle
- **Descrição**: Altera os dados de um cliente existente.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do cliente a ser alterado.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**:
    ```json
    {
      "endereco": "string",
      "telefone": "98464160189",
      "dataNascimento": "2024-05-24T12:59:13.893Z",
      "usuario": {
        "nome": "string",
        "email": "string",
        "senha": "string"
      }
    }
    ```
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 400 Bad Request: Requisição inválida.
  - 403 Unauthorized: Acesso Negado.

#### 4.5 Remover Cliente
- **URL**: /clientes/remover/{id}
- **Método**: DELETE
- **Tags**: cliente-controle
- **Descrição**: Remove um cliente específico.
- **Parâmetros**
  - id (obrigatório, path, integer, int64): ID do cliente.
- **Respostas**
  - 200 OK: Cliente removido com sucesso.
  - 403 Unauthorized: Acesso negado.

### 5. Gerenciamento de Serviços
#### 5.1 Listar Serviços
- **URL**: /servicos
- **Método**: GET
- **Tags**: servico-controle
- **Descrição**: Lista todos os serviços disponíveis.
-**Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 5.2 Criar Serviço
- **URL**: /servicos
- **Método**: POST
- **Tags**: servico-controle
- **Descrição**: Cria um novo serviço.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**:
    ```json
    {
      "categoria": "string",
      "subcategoria": "string",
      "titulo": "string",
      "descricao": "string",
      "preco": 0,
      "estaAtivo": true,
      "prestador": "string",
      "fotos": [
        {
          "url": "string",
          "servico": "string"
        }
      ]
    }
    ```
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Serviço criado com sucesso.
  - 400 Bad Request: Solicitação inválida.
  - 403 Unauthorized: Acesso negado.

#### 5.3 Obter Serviço por ID
- **URL**: /servicos/{servicoId}
- **Método**: GET
- **Tags**: servico-controle
- **Descrição**: Obtém um serviço específico pelo ID.
- **Parâmetros**
  - servicoId (obrigatório, path, integer, int64): ID do serviço.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 5.4 Obter Fotos do Serviço
- **URL**: /servicos/{servicoId}/fotos
- **Método**: GET
- **Tags**: servico-controle
- **Descrição**: Obtém as fotos de um serviço específico.
- **Parâmetros**
  - servicoId (obrigatório, path, integer, int64): ID do serviço.
- **Respostas**
  - 200 OK: Solicitação bem-sucedida.
  - 403 Unauthorized: Acesso negado.

#### 5.5 Adicionar Foto ao Serviço
- **URL**: /servicos/{servicoId}/fotos
- **Método**: POST
- **Tags**: servico-controle
- **Descrição**: Adiciona uma foto a um serviço específico.
- **Parâmetros**
  - servicoId (obrigatório, path, integer, int64): ID do serviço.
- **Corpo da Requisição**
  - **Tipo**: application/json
  - **Schema**: FotoModelo
  - **Obrigatório**: true
- **Respostas**
  - 200 OK: Foto adicionada com sucesso.
  - 403 Unauthorized: Acesso negado.
    
 ### Tabela de Códigos de erros
  
   | Código | Descrição                   | Significado                                                                  |
   |--------|-----------------------------|------------------------------------------------------------------------------|
   | 200    | OK                          | A requisição foi bem-sucedida e o servidor retornou os dados solicitados.    |
   | 400    | Bad Request                 | A requisição está malformada ou contém parâmetros inválidos.                 |
   | 403    | Unauthorized                | O servidor entendeu a requisição, mas se recusa a autorizá-la, acesso negado.|

## IDE e versões utilizadas:
Este projeto foi desenvolvido em  Visual Studio Code :
Versão: 1.89.1 (system setup)
Java JDK 17

<h2 id="colab">🤝 Colaboradores</h2>

Todas as pessoas envolvidas no projeto.

<table>
  <tr>
    <td align="center">
  <a href="https://github.com/arlannunesdev">
    <img src="https://avatars.githubusercontent.com/u/162641327?v=4" width="100px;" alt="Arlan Nunes Profile Picture"/><br>
    <sub>
      <b><a href="https://github.com/arlannunesdev">Arlan Nunes</a></b>
    </sub>
  </a>
</td>
<td align="center">
      <a href="https://github.com/Glaydonlima">
        <img src="https://avatars.githubusercontent.com/u/101594250?v=4" width="100px;" alt="Glaydon Lima Profile Picture"/><br>
        <sub>
          <b><a href="https://github.com/Glaydonlima">Glaydon Lima</b></a>
        </sub>
      </a>
    </td>
<td align="center">
      <a href="https://github.com/Hiuryfideliscs">
        <img src="https://avatars.githubusercontent.com/u/170748787?v=4" width="100px;" alt="Hiury Luciano Profile Picture"/><br>
        <sub>
          <b><a href="https://github.com/Hiuryfideliscs">Hiury Luciano</b></a>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/kamily012">
        <img src="https://avatars.githubusercontent.com/u/163183986?v=4" width="100px;" alt="Kamily Vitoria Profile Picture"/><br>
        <sub>
          <b><a href="https://github.com/kamily012">Kamily Vitoria</b></a>
        </sub>
      </a>
    </td>
  </tr>
</table>

## License

---

MIT License
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)
---

Made with ♥ by Start 2024.1 - Event's Hub
   