# Assembleia API
> API Restful solicitada em avaliação técnica. Desenvolvida em SpringBoot

A API é composta de serviços para gerência de votações em assembléias. Os serviços suportam o fluxo padrão: Cadastro de pauta, abertura de sessão de votação, registro de votos, encerramento da votação e emissão de resultado.

## Pré-Requisitos

[GIT](https://git-scm.com/)

[DOCKER](https://www.docker.com/)

[DOCKER COMPOSE](https://docs.docker.com/compose/gettingstarted/)


## Execução

Tudo que você precisa fazer para excutar é:

```
git clone https://github.com/daniloalexandre/assembleia-api
cd assembleia-api
docker-compose up -d

```

## Uso

O fluxo padrão inicia com a criação de uma Pauta, segue para o cadastro de uma votação, inicia-se os cadastros de votos, finaliza-se com a emissão de resultado de votação.

Você pode usar os end-points associados a cada etapa do fluxo através do link [http://localhost:8081/v1/assembleia/swagger-ui.html](http://localhost:8081/v1/assembleia/swagger-ui.html). Lá existem mais detalhes sobre cada end-point existente.

## Configuração para Desenvolvimento

Para desenvolvimento do código do projeto foi utilizada a IDE Spring Tool Suite. [DOWNLOAD](https://spring.io/tools)  

Após clonar o projeto para o diretório de sua escolha. Inicie o STS, siga para o menu `File > Import...`, dentre as opções existentes escolha `Maven > Existing Maven project`. 

A IDE irá configurar e baixar automaticamente as dependêcias. 

Ajuste as configurações do MySQL e Apache Kafka no arquivo __application.properties__ presente no diretório __resources__  de acordo com o seu ambiente de execução.

## Considerações sobre o desenvolvimento do código 

O versionamento de API adotado foi controle de versão no path da contexto (dev-friendly), por exemplo, `http://{domain}/v1/assembleia`. [Leia mais](https://thiagolima.blog.br/parte-4-versionando-apis-restful-b1dd33c65a9c)

O projeto encontra-se estruturado nos seguintes pacotes:

### async

Contém os compoentes de tasks assincronas do sistema, implementadas com uso de Timer do JAVA. 

### config

Contém as configurações de segurança de acesso aos serviços. Atualmente, são permitias requisições de qualquer origem para os métodos GET, POST, PUT e DELETE de qualquer end-point.

### controller

Contém as classes reponsáveis por gerenciar as requisições

### dto

Contém as entidades que representam os conteúdos das requisições e das repostas às requisições

### exception

Contém os handlers pra controle de exceções do sistema.

### l10n

Contém os componentes de gerência de Internacionalização e Localização do sistema 

### mapper

Contém os componentes que mapeiam as entidades de DTO em entidades de negócio do sistema e vice-versa. Desenvolvidos com [MapStruct](https://mapstruct.org/) 

### message

Contém os serviços de mensageria. Desenvolvidos com [Spring Kafka](https://spring.io/projects/spring-kafka) 

### model

Contém as entidades que representam o modelo de negócio do sistema.

### repository

Contém as intefaces de gerencimanto e controle de armazenamento das entidades de negócio.

### service 

Contém as interfaces e implementações dos serviços que gerenciam as regras de negócio do sistema.

### utils

Classes utilitárias para manipulação de estruturas de dados diversas.

### webclient

Contém os componentes clientes para consumo de serviços de terceiros. Desenvolvido usando Spring Reactive

Observação: O componente WebClient do spring empregado na solução é uma alternativa ao RestTemplate. [Veja mais](https://www.baeldung.com/spring-5-webclient)



## Histórico de lançamentos

* 0.0.1
    * Trabalho em andamento

## Meta

Danilo Alexadre – daniloalexandre@gmail.com
 
 
