Repositório dedicado para o desenvolvimento da primeira snapshot do projeto artbox, projeto que visa o desenvolvimento de uma ApiRestful utilizando Springboot, Hibernate, e MySQL
o desenvolvimento desta snapshot será principalmente para inicializar os testes unitários e polir as funcionalidades do software.

Tecnologias Utilizadas:
Backend
Java 21
Spring Boot (Web Starter, JPA, Spring Security, DevTools)
MySQL Driver
Maven (ou Gradle)
Testes
JUnit
Mockito
Frontend
React
Containerização
Docker
IDE
IntelliJ IDEA
Pré-Requisitos
Java 21 instalado
Maven ou Gradle instalado
MySQL instalado e configurado
Docker instalado (para containerização e ambiente isolado)


**Roadmap do Projeto**
Planejamento & Levantamento de Requisitos

Levantar e documentar os requisitos do sistema junto aos stakeholders.
Definir casos de uso, endpoints e fluxos principais (vendas, gerenciamento de clientes, emissão de notas).
Configuração do Ambiente de Desenvolvimento

Configuração do repositório Git e definição de branchs (ex.: main, develop, feature/*).
Setup inicial dos projetos: backend (Spring Boot) e frontend (React).
Desenvolvimento do Backend

Modelagem do Domínio: Criação das entidades (Vendas, Clientes, Notas Fiscais).
Persistência: Configuração do JPA e criação das repositórios.
Endpoints RESTful: Desenvolvimento dos controllers para expor as funcionalidades.
Segurança: Implementar Spring Security para acesso restrito (usuário ADMIN).
Testes: Criação de testes unitários com JUnit e Mockito.
Desenvolvimento do Frontend

Configuração do projeto React e estrutura inicial.
Integração com a API RESTful.
Desenvolvimento de telas para gerenciamento de vendas, clientes e emissão de notas fiscais.
Integração e Testes

Integração contínua (CI) e testes de integração entre frontend e backend.
Testes de performance e usabilidade.
Correção de bugs e refinamento do sistema.
Containerização e Deploy

Criação do Dockerfile para o backend (e futuramente para o frontend, se necessário).
Configuração do docker-compose.yml para orquestrar os containers (API, MySQL e possivelmente frontend).
Deploy em ambiente de testes (snapshot) para validação de todas as funcionalidades.
Documentação & Finalização

Atualização e melhoria contínua da documentação (README, Swagger/OpenAPI, etc.).
Revisão final de segurança, performance e usabilidade.
Preparação do projeto para exposição no portfólio e deploy final.
