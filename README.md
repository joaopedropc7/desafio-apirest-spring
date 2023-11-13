# API REST DESAFIO

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](	https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)

Este projeto usa Java, Spring, banco de dados PostgreSql e Spring Security com JWT! Também está documentada com o Swagger

Esta api rest é um desafio, na qual consta com endpoint para login, registro, update de conta e senha, contém um CRUD de posts com a opcão de like e unlike, após o registro de conta é enviado um email para o email fornecido com o token para verificacão, este token se expira em 30 minutos para isto foi usado o SMTP do gmail e o Java Mail Sender, além disso, também conta com um endpoint que faz o download de um PDF com os termos de serviço.

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/joaopedropc7/desafio-apirest-spring.git
```

2. Instale as dependências com o Maven

3. Instale [PostgreSql](https://www.postgresql.org/download/)


## Inicio

1. Gere o .jar ou compile o projeto usando o maven.
2. A API ficará acessível em http://localhost:8080


## API Endpoints
Siga esses endpoints para acessar a API:

```markdown
GET /api/posts - Lista todos os planos.

GET /api/posts/{id} - Busca um plano por id

PUT api/posts/{id} - Atualiza um post pelo ID 

Delete /api/posts/{id} - Deleta um post pelo ID 

POST /api/posts/like/{id} - Curte um post

POST /api/posts/unlike/{id} - Descurte um post

POST /api/posts/search - Busca um post desta forma -> /api/posts/search?query={parametro}
-----------------------------------------

POST /api/authentication/login - Login

POST /api/authentication/register - Register

Put /api/authentication/uodate - Atualia a conta

Put /api/authentication/uodate/password - Atualia a conta

POST /api/authentication/verified/{id} - Verifica a conta fornecendo o id da conta na url e passando como parâmetro no body o token enviado por email

-----------------------------------------

GET /api/terms - Download PDF com os termos de uso e políticas de privacidade(PDF)
```

## Authentication
A API usa Spring Security para controle de autenticacão. Estas são as roles disponíveis:

```
USER -> Usuário padrão
ADMIN -> Usuário admin.
```

## Database
Este projeto utiliza [PostgreSql](https://www.postgresql.org/download/) database.










