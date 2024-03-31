# Logging

## Logback

> Os "starters" do Spring Boot usam o **Logback** como ferramenta de _Logging_.

* Fonte: [A Guide To Logback](https://www.baeldung.com/logback)
*   Dependências:

    ```markup
      <properties>
          <ch.qos.logback.version>1.2.3</ch.qos.logback.version>
      </properties>
      <dependencyManagement>
          <dependencies>
              <dependency>
                  <groupId>ch.qos.logback</groupId>
                  <artifactId>logback-core</artifactId>
                  <version>${ch.qos.logback.version}</version>
              </dependency>
              <dependency>
                  <groupId>ch.qos.logback</groupId>
                  <artifactId>logback-classic</artifactId>
                  <version>${ch.qos.logback.version}</version>
              </dependency>
              <dependency>
                  <groupId>ch.qos.logback</groupId>
                  <artifactId>logback-access</artifactId>
                  <version>${ch.qos.logback.version}</version>
              </dependency>
          </dependencies>
      </dependencyManagement>
    ```

A arquitetura do Logback é dividida em 3 camadas descritas a seguir: Logger, Appender e Layout.

### Camada Logger

*   Trata-se da classe que a nossa aplicação interage/instancia para fazer o Logging.

    ```java
      import org.slf4j.Logger;
      import org.slf4j.LoggerFactory;

      public class Exemplo {

          private final Logger logger = LoggerFactory.getLogger(Exemplo.class);

          public void log() {
              logger.info("Log de informação");
              logger.warn("Log de aviso, algo está errado ou faltando! cuidado!");
              logger.error("Log de erro, algo de errado aconteceu!");
              logger.debug("Log de depuração, contém informações mais refinadas, que são mais úteis para depurar um aplicativo");
              logger.trace("Log de rastreabilidade, contém informações mais refinadas do que o DEBUG");
          }
      }
    ```
* Cada logger tem um **level**, que pode ser configurado via arquivo de configuração ou pelo método `Logger.setLevel()`. Os níveis tem uma ordem de precedência: TRACE < DEBUG < INFO < WARN < ERROR.
* Isso significa que um Logger com nível configurado igual a WARN não exibirá mensagens de TRACE, DEBUG ou INFO.

> Há uma hierarquia em relação aos loggers (o Logger **root** está no topo). Se não for especificado o nível do Logger, ele herdará do Logger superior. Se nenhum nível for especificado, o nível dos Loggers será o default para o root, que é DEBUG.

*   Exemplo:

    ```java
      Logger parentLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.logback"); 
      parentLogger.setLevel(Level.INFO); 

      Logger childlogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.logback.tests"); 

      parentLogger.warn("This message is logged because WARN > INFO."); 
      parentLogger.debug("This message is not logged because DEBUG < INFO."); 
      childlogger.info("INFO == INFO");
      childlogger.debug("DEBUG < INFO");
    ```

    `20:31:29.586 [main] WARN com.logback - This message is logged because WARN > INFO.`\
    `20:31:29.594 [main] INFO com.logback.tests - INFO == INFO`
*   Informamos parâmetros na mensagem através de `{}`. Isso é melhor que simplesmente concatenar Strings pois dessa forma o custo de construir a mensagem só é feito quando realmente ela for logada.

    ```java
      log.debug("Mensagem é {}", mensagem);
    ```

> ### É possível passar uma exceção como parâmetro como **último argumento do método**. O Logger irá printar o stack trace da exceção informada.
>
> #### Arquivo de Configuração

* O arquivo de configuração deve estar no classpath (na pasta resources) e deve ser nomeado logback-spring.xml, logback-test.xml ou logback.xml.
* Exemplo de arquivo de configuração:

```markup
<configuration>  
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">  
        <encoder>  
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>  
        </encoder>
    </appender> 
    <root level="debug">  
        <appender-ref ref="STDOUT" /> 
    </root> 
</configuration>
```

1. Usamos o appender `ConsoleAppender` pois queremos que os logs sejam mostrados no console.
2.  Na tag encoder informamos como desejamos ver os Logs. Nesse caso, usamos um pattern de exibição. Esse pattern em específico loga a mensagem no seguinte formato:

    `20:44:44.241 [main] DEBUG com.logback - Hi there!`
3. Na tag root definimos o nível do Logger-pai, e associamos ele ao appender definido anteriormente.
4.  Quer saber se a configuração está escrita corretamente? Use o parâmetro `debug=true` na tag de configuration:

    ```markup
     <configuration debug="true">
         ...
     </configuration>
    ```
5.  Como dito anteriormente, se não for informado o nível do Logger, ele herdará o nível do Logger pai. Podemos informar isso com:

    ```markup
     <logger name="com.logback" level="INFO" />
    ```
6.  Para variáveis que repetem, podemos adicionar propriedades através da tag `property`.

    ```markup
     <property name="LOG_DIR" value="/var/log/application" />  
     <appender name="FILE" class="ch.qos.logback.core.FileAppender">              
         <file>${LOG_DIR}/tests.log</file>
             ...
     </appender>
    ```

    Propriedades também podem ser configuradas com outros meios, como por exemplo variáveis de ambiente:

    ```
     `java -DLOG_DIR=/var/log/application`
    ```

### Appenders

* Os Loggers informam aos appenders a mensagem a ser logada, e estes são os que realmente fazem o logging. Os Appenders são responsáveis por determinar como os dados serão mostrados.
* Tipos de Appenders:
* ConsoleAppender: printa a mensagem no console.
*   FileAppender: escreve os logs no arquivo informado pela tag file. A tag append configurada para true informa que queremos que os logs sejam escritos no mesmo arquivo.

    ```markup
     <appender name="FILE"  class="ch.qos.logback.core.FileAppender">
         <file>tests.log</file>
         <append>true</append> 
         <encoder> 
             <pattern>%-4relative [%thread] %-5level %logger{35} - %msg%n</pattern>
         </encoder>
     </appender>
    ```
*   RollingFileAppender: escreve os logs em arquivos baseados no tempo, tamanho ou ambos. Para esse appender, podemos definir uma **RollingPolicy** que define quando um novo arquivo de logs deve ser criado. A _TimeBasedPolicy_ usa o padrão de tempo no nome do arquivo (tag fileNamePattern) para determinar o intervalo de criação de arquivos.

    ```markup
     <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">  
         <file>LogFile.log</file>

         <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
             <fileNamePattern>LogFile.%d{yyyy-MM-dd}.gz</fileNamePattern>
             <maxHistory>30</maxHistory>
             <totalSizeCap>3GB</totalSizeCap> 
         </rollingPolicy> 

         <encoder> 
             <pattern>%-4relative [%thread] %-5level %logger{35} - %msg%n</pattern>
         </encoder> 
     </appender>
    ```

    Nesse caso, cada arquivo de log conterá os logs do dia. Se o pattern fosse `%d{yyyy-MM}`, os arquivos seriam criados a cada mês. As tags `maxHistory` e `totalSizeCap` informam o tempo e quantidades máximas de logs que queremos guardar. Nesse caso, guardamos um histórico de 30 dias de logs com no máximo 3GB de tamanho.
*   Podemos usar diferentes appenders no mesmo arquivo de configuração. No entanto, eles são acumulativos, ou seja, um Logger herdará o appender do logger-pai e também usará o appender configurado para ele. Para informar que o Logger deve usar somente o seu appender, informamos o parâmetro `additivity="false"`.

    ```markup
      <configuration debug="true">

          <appender name="STDOUT"  class="ch.qos.logback.core.ConsoleAppender">
              encoder...
          </appender> 

          <appender name="FILE"  class="ch.qos.logback.core.FileAppender"> 
              <file>tests.log</file>
              <append>true</append>
              encoder...
          </appender>

          <logger name="com.baeldung.logback" level="INFO" /> 

          <logger name="com.baeldung.logback.tests" level="WARN" additivity="false"> 
              <appender-ref ref="FILE" />
          </logger>

          <root level="debug"> 
              <appender-ref ref="STDOUT" />
          </root>
      </configuration>
    ```

### Layout

*   A última camada do Logback é o layout. Informa como formatar as mensagens através da tag `encoder`. Os exemplos anteriores usam patterns para formatar as mensagens, mas também podemos loggar em formato JSON, por exemplo, usando o [Logstash Logback Encoder](https://github.com/logstash/logstash-logback-encoder).

    ```markup
      <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>6.4</version>
      </dependency>
    ```
*   Agora, podemos informar o encoder que quisermos no appender. Para o `LoggingEventCompositeJsonEncoder` e `AccessEventCompositeJsonEncoder`, podemos definir os [providers](https://github.com/logstash/logstash-logback-encoder#providers-for-loggingevents) (quais informações queremos no log).

    ```markup
      <configuration>

          <appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
              <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
                  <providers>
                      <timestamp/>
                      <version/>
                      <loggerName/>
                      <threadName/>
                      <logLevel/>
                      <mdc/>
                      <message/>
                      <stackTrace/>
                  </providers>
              </encoder>
          </appender>

          <logger name="jsonLogger" additivity="false" level="DEBUG">
              <appender-ref ref="consoleAppender"/>
          </logger>

          <root level="INFO">
              <appender-ref ref="consoleAppender"/>
          </root>

      </configuration>
    ```
