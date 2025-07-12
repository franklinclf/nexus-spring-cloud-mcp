# Nexus Support - Ticket Service + MCP

![Java](https://img.shields.io/badge/Java-24-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.0.0-blueviolet?logo=spring&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring_AI-1.0.0-orange?logo=spring&logoColor=white)
![MCP](https://img.shields.io/badge/MCP-Model_Client_Pattern-lightblue?logo=spring&logoColor=white)
![Microservices](https://img.shields.io/badge/Architecture-Microservices-lightgrey)

## 📚 Visão Geral do Projeto

Este repositório faz parte do projeto acadêmico Nexus Support, desenvolvido para a disciplina DIM0614 - Programação Distribuída na UFRN. O Nexus Support é um sistema de suporte distribuído que visa otimizar a gestão de chamados (tickets) utilizando uma arquitetura de microsserviços e inteligência artificial. Ele é responsável por:
- Armazenar e gerenciar tickets abertos pelos clientes.
- Servir como **servidor MCP** (Model Client Pattern), respondendo a requisições da IA para triagem de chamados.
- Expor endpoints RESTful tradicionais para manipulação dos tickets via dashboard ou aplicações externas.

## 🚀 Tecnologias Utilizadas

Este microsserviço combina persistência, comunicação reativa e inteligência artificial com:

* **Java 24:** Linguagem de desenvolvimento.
* **Spring Boot 3.5.3:** Framework base.
* **Spring Cloud 2025.0.0:** Integração com o ecossistema de microsserviços.
    * `spring-cloud-starter-config`: Busca de configurações externas.
    * `spring-cloud-starter-netflix-eureka-client`: Descoberta de serviços.
    * `spring-cloud-starter-loadbalancer`: Balanceamento de carga.
* **Spring Data JPA:** Persistência de dados em banco relacional.
* **Spring Validation:** Validação de DTOs e entradas REST.
* **Spring Web + WebFlux:** Suporte a APIs síncronas e assíncronas.
* **Spring AI 1.0.0:**
    * `spring-ai-starter-mcp-server`: Exposição de um servidor compatível com o padrão Model Client Pattern.
* **Micrometer Tracing + Zipkin:** Observabilidade e rastreamento distribuído.
* **Prometheus:** Métricas expostas para monitoramento.
* **PostgreSQL:** Banco de dados relacional.
* **Lombok:** Redução de boilerplate no código.
* **Spring Boot Actuator:** Endpoints de monitoramento e administração.
* **Spring Boot DevTools:** Ferramentas de desenvolvimento ágil.

## ⚙️ Funcionalidades

* 🧾 **Cadastro e consulta de tickets:** CRUD completo com persistência em banco relacional.
* 🧠 **MCP Server:** Exposição de interface compatível com ferramentas de IA utilizando o padrão de modelo cliente.
* 🔀 **Comunicação reativa com serviços IA:** Suporte para chamadas assíncronas e reativas.
* 📡 **Descoberta automática via Eureka:** Integração transparente com os outros microsserviços.
* 🧩 **Configurações externas:** Todas as propriedades sensíveis e dinâmicas são externalizadas via Spring Config Server.
* 📊 **Observabilidade total:** Métricas expostas via Prometheus e traces enviados ao Zipkin.

## 📈 Monitoramento e Observabilidade

* **Actuator:** Endpoints disponíveis em `/actuator`.
* **Prometheus:** Métricas expostas em `/actuator/prometheus`.
* **Zipkin:** Integração com rastreamento distribuído via Micrometer.

## 🗺️ Outros Repositórios do Nexus Support

Este serviço é parte de um ecossistema maior. Você pode encontrar outros componentes do Nexus Support aqui:

* 🧠 **[AI Service](https://github.com/franklinclf/nexus-spring-cloud-ai):** Cliente MCP que consulta este servidor.
* ⚙️ **[Nexus Support - Config Server](https://github.com/franklinclf/nexus-spring-cloud-config)**: Gerenciamento
  centralizado das configurações de todos os microsserviços.
* 🔍 **[Nexus Support - Eureka Discovery Service](https://github.com/franklinclf/nexus-spring-cloud-discovery)**: Serviço
  de descoberta para registrar e localizar outros microsserviços.
* 🧭 **[Nexus Support - Gateway](https://github.com/franklinclf/nexus-spring-cloud-gateway)**: Ponto de entrada unificado
  para todas as requisições, roteando-as para os serviços apropriados.
* ☁️ **[Nexus Support - Serverless Function](https://github.com/franklinclf/nexus-spring-cloud-serverless)**: Componente
  serverless para tarefas específicas ou escaláveis.

---