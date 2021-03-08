# Assembleia API
> API Restful solicitada em avaliação técnica. Desenvolvida em SpringBoot

A API é composta de serviços para gerência de votações em assembléias. Os serviços cobre o fluxo padrão: Cadastro de pauta, abertura de sessão de votação, registro de votos, encerramento da votação e emissão de resultado.

## Pré-Requisitos

JAVA 8+ [Download](https://www.java.com/pt-BR/)

MAVEN 3.6+ [Download](https://maven.apache.org/download.cgi)

MYSQL 5.5+ [Download](https://www.mysql.com/downloads/)

APACHE ZOOKEEPER 3.6+ [Download](http://zookeeper.apache.org/releases.html#download)

APACHE KAFKA 2.7+ [Download](https://kafka.apache.org/downloads)

## Recomendado

SWAGGER [LINK](https://swagger.io/) 

Swagger foi uma das ferramentas usada para Design e Teste da API. O arquivo swagger.yaml encontra-se [AQUI](https://github.com/daniloalexandre/assembleia-api/blob/main/src/main/resources/swagger.yaml) 

## Instalação

Após efetuar o clone do projeto para o diretório de sua escolha, atravás do prompt de comando, acessa a raiz desse diretório e execute:

```sh
mvn install
```

## Testes

Para executar os testes unitários e de integração, no diretório raiz do projeto, através do prompt de compando, faça:

```sh
mvn test
```

## Execução

Supondo que todos os pré-requisitos esteja instalados e o comando __mvn install__ já foi executado, no diretório raiz do projeto, através do prompt de comando, faça:

```sh
mvn exec:java -Dexec.mainClass="br.com.example.AssembleiaApiApplication"
```

ATENÇÃO: as configurações de execução estão no arquivo __application.properties__ localizado no diretório __/resources__ do projeto. Os valores definidos para conexão ao MySQL e uso do Apache KAFKA são padrões. Lembre-se de ajusta-los de acordo com seu ambiente de execução.

## Exemplo de uso

O fluxo padrão de uso se inicia com o cadastro de Pautas __(POST /pautas)__, abertura de votação __(POST /votacoes)__, seguido do registro de voto __(POST /votacoes/{idVotacao}/votos)__ . 

O encerramento da votação é automático. Após o encerramento, O resultado da votação pode ser obtido através de end-point __(GET /votacoes/{idVotacao})__ . Ainda, um serviço de mensageria envia o resultado para os consumidores no tópico __votacao.resultado__

Para mais informações sobre outros end-points, acesse o arquivo [swagger.yaml](https://github.com/daniloalexandre/assembleia-api/blob/main/src/main/resources/swagger.yaml)

## Configuração para Desenvolvimento

Para desenvolvimento do código do projeto foi utilizada a IDE Spring Tool Suite. [DOWNLOAD](https://spring.io/tools)  

Após clonar o projeto para o diretório de sua escolha. Inicie o STS, siga para o menu `File > Import...`, dentre as opções existentes escolha `Maven > Existing Maven project`. 

A IDE irá configurar e baixar automaticamente as dependêcias. 

Ajuste as configurações do MySQL e Apache Kafka no arquivo __application.properties__ presente no diretório __resources__  de acordo com o seu ambiente de execução.

## Considerações sobre o desenvolvimento do código 

O projeto encontra-se estruturado nos seguintes pacotes:

### async

Contém os compoentes de tasks assincornas, para controle das sessões de votação. Desenvolvidos com Spring Scheduling.

Observação: Outras soluções poderiam ser utilizadas, como o uso direto da classe Timer do JAVA. A adoção do Spring Scheduling foi pela facilidade de configuração do pool de threads.

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

Observação: Outros serviços de mesageria foram analizados, como por exemplo o RabbitMQ. A adoção do Kafka se deu pela simplicidade de configuração.

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
 
 
