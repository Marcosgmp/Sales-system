# Sales System API

API REST desenvolvida em **Spring Boot** para gerenciamento de vendas, usuários, produtos e carrinho de compras, com **autenticação JWT** e **controle de acesso por roles (USER / ADMIN)**.

Projeto criado com foco em **boas práticas**, **arquitetura em camadas** e **organização profissional**, sendo ideal para portfólio backend.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- Spring Web
- Spring Security
- Spring Data JPA (Hibernate)
- JWT (JSON Web Token)
- BCrypt Password Encoder
- PostgreSQL / MySQL
- Maven
- Swagger

---

## 🏗️ Arquitetura do Sistema

A aplicação foi desenvolvida seguindo o padrão de arquitetura em camadas,
garantindo separação de responsabilidades, escalabilidade e facilidade de manutenção.

![Arquitetura do Sistema](./Docs/architecture.png)

### Camadas da Aplicação

- **Controller Layer**  
  Responsável por expor os endpoints REST, receber requisições HTTP e retornar respostas apropriadas.

- **Service Layer**  
  Contém a lógica de negócio da aplicação, validações e regras do domínio.

- **Domain Layer**  
  Representa o núcleo do sistema, contendo as **entidades JPA** e os **DTOs** utilizados para entrada e saída de dados.

- **Data Access Layer (Repository)**  
  Camada de persistência responsável pela comunicação com o banco de dados, utilizando **Spring Data JPA / Hibernate**.

- **Security Layer**  
  Responsável pela autenticação e autorização da aplicação, utilizando **JWT** integrado ao **Spring Security**.

- **Database**  
  Banco de dados relacional (**PostgreSQL**, com possibilidade de uso de MySQL).

---

## 📁 Estrutura de Pastas

```
src/
├── main/
│   ├── java/
│   │   └── com.sales.system/
│   │       ├── controller/
│   │       │   ├── admin/
│   │       │   ├── auth/
│   │       │   └── user/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repository/
│   │       ├── security/
│   │       ├── service/
│   │       └── SystemApplication.java
│   └── resources/
│       └── application.properties
└── test/
```
#### 📌 Principais Endpoints

##### Autenticação
- POST `/api/auth/register`
- POST `/api/auth/login`

##### Produtos
- GET `/api/products`
- GET `/api/products/{id}`

##### Carrinho
- GET `/api/cart`
- POST `/api/cart/items`
  
##### Administração (ADMIN)
- POST `/api/admin/products`
- PUT `/api/admin/products/{id}`

---

## 🔐 Segurança e Autenticação

A aplicação utiliza **JWT (JSON Web Token)** para autenticação stateless.

### Fluxo de Autenticação

1. Usuário realiza login via `/api/auth/login`
2. Credenciais são validadas
3. Um **JWT** é gerado e retornado
4. O token deve ser enviado no header:

```
Authorization: Bearer <token>
```

### Controle de Acesso

- **Rotas públicas**:
  - `/api/auth/**`
  - `/api/products/**`

- **Rotas protegidas**:
  - `/api/admin/**` → apenas `ROLE_ADMIN`
  - Demais rotas → usuário autenticado

---

## 👥 Roles do Sistema

- **USER**: acesso a funcionalidades básicas (produtos, carrinho, perfil)
- **ADMIN**: gerenciamento de usuários, produtos, roles e carrinho

---

## 🗄️ Modelo de Dados (Resumo)

### Principais Entidades

- **User**
- **Roles**
- **UserRole** (tabela associativa)
- **Product**
- **Cart**
- **CartItem**
- **Address**

### Relacionamentos

- User **1:N** Cart
- Cart **1:N** CartItem
- Product **1:N** CartItem
- User **N:N** Roles (via UserRole)
- User **1:1** Address

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL ou MySQL

### Configuração de ambiente

Edite o arquivo `application.properties` e defina as variáveis de ambiente:

- DB_URL
- DB_USER
- DB_PASSWORD

### Passos

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git

# Entrar no projeto
cd sales-system-API

# Rodar a aplicação
mvn spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080

```
## 📘 Documentação da API (Swagger)

A API possui documentação interativa via Swagger/OpenAPI.

Após iniciar a aplicação, acesse:
http://localhost:8080/swagger-ui/index.html

Para acessar endpoints protegidos:
1. Faça login em `/api/auth/login`
2. Copie o token JWT retornado
3. Clique em **Authorize** no Swagger
4. Cole o token no formato: `Bearer <token>`

---

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais e portfólio profissional.

---

## 👤 Autor

**Marcos Gustavo Mendonça Pereira**  
Estudante de Ciência da Computação (IFCE)  
Backend Developer
