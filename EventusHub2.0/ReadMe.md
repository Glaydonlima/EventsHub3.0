# OpenAPI Definition

## API Information
- Title: OpenAPI definition
- Version: v0

## Servers
- URL: http://localhost:8080
- Description: Generated server url

## Endpoints


### 1. Alterar Usuário
- URL: /usuario/alterar/{id}
- Method: PUT
- Tags: usuario-controle
- Description: Altera os dados de um usuário existente.

#### Parameters
- id (required, path, integer, int64): ID do usuário a ser alterado.

#### Request Body
- Content Type: application/json
- Schema: [PLACEHOLDER_FOR_USUARIO_SCHEMA]
- Required: true

#### Responses
- 200 OK: Solicitação bem-sucedida.
- 400 Bad Request: Requisição inválida


### 2. Alterar Prestador
- URL: /prestador/alterar/{id}
- Method: PUT
- Tags: prestador-controle
- Description: Altera os dados de um prestador existente.

#### Parameters
- id (required, path, integer, int64): ID do prestador a ser alterado.

#### Request Body
- Content Type: application/json
- Schema: [PLACEHOLDER_FOR_PRESTADOR_SCHEMA]
- Required: true

#### Responses
- 200 OK: Solicitação bem-sucedida.
- 400 Bad Request: Requisição inválida.


3. Alterar Cliente
URL: /clientes/alterar/{id}
Método: PUT
Tags: cliente-controle
Descrição: Altera os dados de um cliente existente.

Parâmetros
id (obrigatório, path, integer, int64): ID do cliente a ser alterado.
Corpo da Requisição
Tipo: application/json
Schema: ClienteModelo
Obrigatório: true
Respostas
200 OK: Solicitação bem-sucedida.
4. Login de Usuário
URL: /usuario/login
Método: POST
Tags: usuario-controle
Descrição: Realiza o login de um usuário.

Corpo da Requisição
Tipo: application/json
Schema: UsuarioModelo
Obrigatório: true
Respostas
200 OK: Login bem-sucedido.
400 Bad Request: Requisição inválida.
5. Cadastrar Usuário
URL: /usuario/cadastrar
Método: POST
Tags: usuario-controle
Descrição: Cadastra um novo usuário.

Corpo da Requisição
Tipo: application/json
Schema: UsuarioModelo
Obrigatório: true
Respostas
200 OK: Cadastro bem-sucedido.
400 Bad Request: Requisição inválida.
6. Listar Serviços
URL: /servicos
Método: GET
Tags: servico-controle
Descrição: Lista todos os serviços disponíveis.

Respostas
200 OK: Solicitação bem-sucedida.
7. Criar Serviço
URL: /servicos
Método: POST
Tags: servico-controle
Descrição: Cria um novo serviço.

Corpo da Requisição
Tipo: application/json
Schema: ServicoModelo
Obrigatório: true
Respostas
200 OK: Serviço criado com sucesso.
8. Obter Fotos do Serviço
URL: /servicos/{servicoId}/fotos
Método: GET
Tags: servico-controle
Descrição: Obtém as fotos de um serviço específico.

Parâmetros
servicoId (obrigatório, path, integer, int64): ID do serviço.
Respostas
200 OK: Solicitação bem-sucedida.
9. Adicionar Foto ao Serviço
URL: /servicos/{servicoId}/fotos
Método: POST
Tags: servico-controle
Descrição: Adiciona uma foto a um serviço específico.

Parâmetros
servicoId (obrigatório, path, integer, int64): ID do serviço.
Corpo da Requisição
Tipo: application/json
Schema: FotoModelo
Obrigatório: true
Respostas
200 OK: Foto adicionada com sucesso.
10. Cadastrar Prestador
URL: /prestador/cadastrar/{id}
Método: POST
Tags: prestador-controle
Descrição: Cadastra um novo prestador.

Parâmetros
id (obrigatório, path, integer, int64): ID do prestador.
Corpo da Requisição
Tipo: application/json
Schema: PrestadorModelo
Obrigatório: true
Respostas
200 OK: Cadastro bem-sucedido.
11. Cadastrar Cliente
URL: /clientes/cadastrar/{id}
Método: POST
Tags: cliente-controle
Descrição: Cadastra um novo cliente.

Parâmetros
id (obrigatório, path, integer, int64): ID do cliente.
Corpo da Requisição
Tipo: application/json
Schema: ClienteModelo
Obrigatório: true
Respostas
200 OK: Cadastro bem-sucedido.
12. Listar Usuários
URL: /usuario/listar
Método: GET
Tags: usuario-controle
Descrição: Lista todos os usuários.

Respostas
200 OK: Solicitação bem-sucedida.
400 Bad Request: Requisição inválida.
13. Obter Serviço por ID
URL: /servicos/{servicoId}
Método: GET
Tags: servico-controle
Descrição: Obtém um serviço específico pelo ID.

Parâmetros
servicoId (obrigatório, path, integer, int64): ID do serviço.
Respostas
200 OK: Solicitação bem-sucedida.
14. Obter Prestador por ID
URL: /prestador/{id}
Método: GET
Tags: prestador-controle
Descrição: Obtém um prestador específico pelo ID.

Parâmetros
id (obrigatório, path, integer, int64): ID do prestador.
Respostas
200 OK: Solicitação bem-sucedida.
15. Listar Prestadores
URL: /prestador/listar
Método: GET
Tags: prestador-controle
Descrição: Lista todos os prestadores.

Respostas
200 OK: Solicitação bem-sucedida.
16. Listar Clientes
URL: /clientes/listar
Método: GET
Tags: cliente-controle
Descrição: Lista todos os clientes.

Respostas
200 OK: Solicitação bem-sucedida.
17. Remover Usuário
URL: /usuario/remover/{id}
Método: DELETE
Tags: usuario-controle
Descrição: Remove um usuário específico.

Parâmetros
id (obrigatório, path, integer, int64): ID do usuário.
Respostas
200 OK: Usuário removido com sucesso.
400 Bad Request: Requisição inválida.
18. Remover Prestador
URL: /prestador/remover/{id}
Método: DELETE
Tags: prestador-controle
Descrição: Remove um prestador específico.

Parâmetros
id (obrigatório, path, integer, int64): ID do prestador.
Respostas
200 OK: Prestador removido com sucesso.
19. Remover Cliente
URL: /clientes/remover/{id}
Método: DELETE
Tags: cliente-controle
Descrição: Remove um cliente específico.

Parâmetros
id (obrigatório, path, integer, int64): ID do cliente.
Respostas
200 OK: Cliente removido com sucesso.
Componentes
Schemas
UsuarioModelo
Tipo: object
Propriedades:
id (integer, int64): ID do usuário.
nome (string): Nome do usuário.
email (string): Email do usuário.
senha (string): Senha do usuário.
PrestadorModelo
Tipo: object
Propriedades:
id (integer, int64): ID do prestador.
nome (string): Nome do prestador.
email (string): Email do prestador.
servicos (array): Lista de serviços prestados.
Items: string
ClienteModelo
Tipo: object
Propriedades:
id (integer, int64): ID do cliente.
nome (string): Nome do cliente.
email (string): Email do cliente.
ServicoModelo
Tipo: object
Propriedades:
id (integer, int64): ID do serviço.
descricao (string): Descrição do serviço.
preco (number): Preço do serviço.
FotoModelo
Tipo: object
Propriedades:
id (integer, int64): ID da foto.
url (string): URL da foto.
