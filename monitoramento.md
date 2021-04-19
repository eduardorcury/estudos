---
description: Contéudo sobre métricas e Prometheus.
---

# Monitoramento

## Métricas

### Importância

As métricas são importantes para auxiliar o _troubleshooting_ caso algum problema ocorra e garantir a qualidade do software em produção.

### Método RED

O método RED \(Rate, Errors & Duration\) analisa a quantidade de solicitações por segundo processadas pelo serviço \(rate\), a quantidade de falhas nas solicitações \(errors\) e a duração de processamento de uma solicitação \(duration\).

> Using these basic metrics most problems that an end user might face with your web app can be captured, such as how many errors there are and how slow their service is \([fonte](https://www.weave.works/docs/cloud/latest/tasks/monitor/best-instrumenting/)\).

Uma das limitações desse método é que ele só pode ser usado em aplicações que lidam com requisições, sendo inadequado em sistemas _batch_ ou de _streaming_.

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





