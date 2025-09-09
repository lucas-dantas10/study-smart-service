# 📚 Study Smart

O **Study Smart** é uma aplicação web focada em **aprendizado com flashcards**, utilizando a técnica de **aprendizado espaçado (Spaced Repetition)** para ajudar você a manter na memória os conteúdos mais importantes de forma eficiente.

---

## 📑 Sumário
- [🚀 Sobre o Projeto](#-sobre-o-projeto)
- [🛠️ Stacks Utilizadas](#️-stacks-utilizadas)
- [🏛️ Arquitetura](#-arquitetura)
- [🔑 Autenticação](#-autenticação)
- [📖 Documentação da API](#-documentação-da-api)
- [⚙️ Como Executar o Projeto](#️-como-executar-o-projeto)
- [👨‍💻 Autor](#-autor)

---

## 🚀 Sobre o Projeto
O objetivo do Study Smart é oferecer uma plataforma simples e eficaz para **organizar, revisar e consolidar conhecimentos**.  
Através de um sistema de cards, o usuário consegue revisar conteúdos em intervalos programados, maximizando a retenção de informações.

---

## 🛠️ Stacks Utilizadas
- **Backend**: [Spring Boot](https://spring.io/projects/spring-boot)
- **Segurança/Autenticação**: [Spring Security](https://spring.io/projects/spring-security) com **OAuth2 (Login via Google)**
- **Documentação da API**: [Swagger / OpenAPI](https://swagger.io/)
- **Linguagem**: Java 17+

---

## 🏛️ Arquitetura
O projeto segue o padrão de **Arquitetura Hexagonal (Ports and Adapters)**, que garante:
- Separação clara entre regras de negócio e detalhes de infraestrutura.
- Facilidade na manutenção e evolução do sistema.
- Independência entre domínios centrais e frameworks externos.

Estrutura geral:
```
📂 study_smart_service
 ├── 📂 src/main/java/com/exemplo/pagamentos
 │    ├── 📂 adapter           # Exposição de APIs REST e Chamadas externas
 │    ├── 📂 application       # Use cases das regras
 │    ├── 📂 domain            # Regras de negócio
 │    ├── 📂 infra             # Configurações
 │    ├── 📂 utils             # Utilidades gerais
 │    ├── 📄 Application.java
```
---

## 🔑 Autenticação
O login é realizado via **Google OAuth2**, permitindo autenticação segura e simplificada para os usuários.

---

## 📖 Documentação da API
A documentação completa da API está disponível via **Swagger UI**:

http://localhost:8080/swagger-ui/index.html

---

## ⚙️ Como Executar o Projeto

### 1. Subir o banco de dados
Certifique-se de ter o **Docker** instalado. No diretório raiz do projeto, rode:

```bash
docker-compose up -d
```

Isso irá iniciar o container do banco MySQL.

### 2. Build da aplicação
Crie um arquivo `.env` (ou defina as variáveis no ambiente) com os seguintes valores:

```yaml
SERVER_PORT=8080
DATABASE_URL=jdbc:mysql://localhost:3306/study_smart
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha

GOOGLE_CLIENT_ID=sua_client_id
GOOGLE_CLIENT_SECRET=sua_client_secret

JWT_SECRET=seu_jwt_secret
JWT_EXPIRATION=3600
JWT_FRONTEND_REDIRECT_URL=http://localhost:3000/auth/callback

FRONTEND_URI=http://localhost:3000
```

### 3. Rodar a aplicação

Caso tenha configurado manualmente o `application.yaml` e tiver o `maven` instalado, basta executar o comando abaixo:

```bash
./mvnw spring-boot:run
```

Caso queira executar via `docker`, então execute o seguinte comando:

```bash
docker run --env-file .env -p 8080:8080 study-smart-service
```

---

## 👨‍💻 Autor
Desenvolvido por **Lucas Dantas**.
