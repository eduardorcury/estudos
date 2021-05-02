---
description: Contéudo sobre métricas e tracing.
---

# Monitoramento

## Métricas

### Importância

As métricas são importantes para auxiliar o _troubleshooting_ caso algum problema ocorra e garantir a qualidade do software em produção.

### Método RED

O método RED \(Rate, Errors & Duration\) analisa a quantidade de solicitações por segundo processadas pelo serviço \(rate\), a quantidade de falhas nas solicitações \(errors\) e a duração de processamento de uma solicitação \(duration\).

> Using these basic metrics most problems that an end user might face with your web app can be captured, such as how many errors there are and how slow their service is \([fonte](https://www.weave.works/docs/cloud/latest/tasks/monitor/best-instrumenting/)\).

{% hint style="info" %}
Uma das limitações desse método é que ele só pode ser usado em aplicações que lidam com requisições, sendo inadequado em sistemas _batch_ ou de _streaming_.
{% endhint %}

### Método USE

O método USE \(Utilization, Saturation & Error\) aborda as métricas de utilização de um recurso \(e. g. a média da porcentagem de utilização de CPU em um dado mês\), o quanto o serviço está trabalhando mais que sua capacidade \(e .g. quantidade de requisições em uma fila de espera\) e a quantidade de requisições que resultaram em erro.

> For every resource, check utilization, saturation, and errors \([fonte](http://www.brendangregg.com/usemethod.html)\).
>
> **resource**: all physical server functional components \(CPUs, disks, busses, ...\) 
>
> **utilization**: the average time that the resource was busy servicing work 
>
> **saturation**: the degree to which the resource has extra work which it can't service, often queued
>
> **errors**: the count of error events

Como exemplos de recursos de servidores, temos:

* CPUs: sockets, cores, hardware threads \(virtual CPUs\)
* Memory: capacity
* Network interfaces
* Storage devices: I/O, capacity
* Controllers: storage, network cards
* Interconnects: CPUs, memory, I/O

## Prometheus

> Projeto open source de monitoramento de aplicações, apoiado pela [Cloud Native Computing Foundation](https://cncf.io/).

[Documentação](https://prometheus.io/docs/introduction/overview/)

![](https://prometheus.io/assets/architecture.png)

* **Pull Method:** o servidor do Prometheus se encarrega de pegar as métricas através de requisições HTTP. Recomendado na maioria das APIs.
* **Push Method:** a aplicação é responsável por enviar as métricas a um _Pushgateway._ Recomendado apenas para processos _batch_ e _serverless_.

### Arquivo de configuração

Arquivo YAML usado para configuração, onde informamos o intervalo de captura de métricas, configuração de alertas, aplicações monitoradas, etc. Exemplo de arquivo de configuração:

```yaml
# Arquivo de configuração do Prometheus
global:
  scrape_interval:     15s # Intervalo de obtenção de métricas.
  evaluation_interval: 15s # Intervalo de checagem de regras.

# Configuração de alertas
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093

# Arquivos de regras (rules). Checados periodicamente de acordo com o evaluation_interval
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# Configuração de endpoint de monitoramento
scrape_configs:
  
  - job_name: 'prometheus'

    # Importante: endpoint padrão de coleta de métricas é '/metrics'

    static_configs:
    - targets: ['localhost:9090']
```

### Notação

```text
<nome da métrica>{<label name>=<label value>, ...}
```

Ou seja, se quisermos métricas da quantidade de requisições POST ao path "/auth" da nossa API, informamos ao Prometheus:

```text
api_http_requests_total{method="POST", handler="/messages"}
```

### Prometheus com Spring Boot Actuator

* Habilitar o endpoint no arquivo .properties:

```text
management.endpoints.web.exposure.include=info,health,prometheus
```

| Endpoint | Descrição \([fonte](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints)\) |
| :--- | :--- |
| prometheus |  Exposes metrics in a format that can be scraped by a Prometheus server. Requires a dependency on  `micrometer-registry-prometheus`. |

Adicionar dependência do Micrometer:

```markup
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

## OpenTracing

[Especificação ](https://opentracing.io/specification/)para rastreamento de requisições que passam por diversas aplicações em sistemas distribuídos. Conceitos importantes:

1. **Trace:** trata-se do "caminho" que compreende uma requisição e todas as ações derivadas dela. Um _trace_ é formado por diversos _spans_.
2. **Spans:** cada operação realizada em um _trace_. Tem os seguintes atributos: nome, início/fim, _tags_, _logs_ e _baggages_.
3. **Tags:** Informações como nome do servico, método HTTP, etc.
4. **Baggages:** Informações que trafegam entre spans diferentes. Pode ser qualquer informação tida como importante no _trace_ como um todo \(e.g. o ID do usuário que fez a requisição\).

```text
––|–––––––|–––––––|–––––––|–––––––|–––––––|–––––––|–––––––|–> time

 [Span A···················································]
   [Span B··············································]
      [Span D··········································]
    [Span C········································]
         [Span E·······]        [Span F··] [Span G··] [Span H··]
```

### OpenTracing com Java

O Spring disponibiliza a classe **Tracer** como implementação da especificação OpenTracing.

```java
public class PropostaController {

  private final Tracer tracer;

  public PropostaController(Tracer tracer) {
    this.tracer = tracer;
  }
  
  @PostMapping
  public void cadastrar(@RequestBody Request request) {
  
    /* Usamos o método activeSpan() para pegar o span.
     * Usamos o método setTag(chave, valor) para criar 
     * uma tag no span. 
     */
    Span activeSpan = tracer.activeSpan();
    activeSpan.setTag("user.email", "email@zup.com.br");
    
    /* Também podemos criar um novo item Baggage com o método 
     * setBaggageItem(chave, valor)
     */
    activeSpan.setBaggageItem("user.email", "email@zup.com.br");
    
    /* O método log(mensagem) é usado para mostrar logs.
     * O Spring por padrão já exibe todos os logs no Jaeger.
     */
    activeSpan.log("Meu log");
    
  }
}
```

## Jaeger

Implementação da especificação OpenTracing, um dos projetos apoiados pela Cloud Native Computing Foundation.



