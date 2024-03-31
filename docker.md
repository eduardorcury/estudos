# Docker

## Comandos Básicos

[Comandos](https://docs.docker.com/engine/reference/commandline/docker/)

1.  Cria um container com a imagem informada. Se o container não executar nada, ele não aparece no `docker ps`.

    ```bash
     docker run NOME_DA_IMAGEM
    ```

    ```bash
     docker run hello-world
    ```
2.  Mostra todos os containers em execução no momento.

    ```bash
     docker ps
    ```
3.  Para mostrar os containers parados também, executamos:

    ```bash
      docker ps -a
    ```
4.  Para mostrar somente os IDs:

    ```bash
      docker ps -q
    ```
5.  Hack para parar todos os containers de uma vez:

    ```bash
      docker stop $(docker ps -q)
    ```
6.  Para executar algo na imagem na hora que estamos criando ela, colocamos o comando que queremos após o nome da imagem:

    ```bash
     docker run ubuntu echo "Olá Mundo"
    ```
7.  Para executar vários comandos na imagem, usamos a flag `-it`. Assim, podemos executar comandos da imagem direto no terminal.

    ```bash
     docker run -it ubuntu
    ```
8.  Para startar um container que está parado:

    ```bash
     docker start ID_CONTAINER
    ```
9.  Para parar um container que está sendo executado:

    ```bash
     docker stop ID_CONTAINER
    ```
10. Comando `attach`: serve para se conectar a containers que estejam rodando. Por padrão, conecta input, output e error ([attach](https://docs.docker.com/engine/reference/commandline/attach/)).

    ```bash
     docker attach ID_CONTAINER
    ```
11. Para não conectar input:

    ```bash
      docker attach --no-stdin ID_CONTAINER
    ```

> Nota: o comando exit dentro do container irá **parar** o container. Usar **Ctrl P + Q** para fazer somente o dettach.

*   Se quisermos startar o container no modo attach, usamos as flags `-ai`:

    ```bash
      docker start -ai ID_CONTAINER
    ```
*   Para remover containers, utilizamos o comando `rm`:

    ```bash
     docker rm ID_CONTAINER
    ```

    Para remover todos os containers parados, usamos o comando `container prune`:

    ```bash
     docker container prune
    ```
*   Para listar as imagens, usamos `images`:

    ```bash
     docker images
    ```
*   Para excluir uma imagem, usamos `rmi`:

    ```bash
      docker rmi NOME/ID
    ```

## Teste site estático

*   Usar a imagem `static-site` do `dockersamples`

    [Comando docker run](https://docs.docker.com/engine/reference/commandline/run/)
*   Usamos o comando `run` com a flag `-d` para executar a imagem no modo dettached. Além disso, a flag -P associa as portas da imagem a uma porta aleatória do nosso computador:

    ```bash
      docker run -d -P dockersamples/static-site
    ```
*   Para ver a porta escolhida:

    ```bash
      docker port ID_IMAGEM
    ```
*   Para especificar um nome para o container, usamos a flag `--name`

    ```bash
      docker run -d -P --name site dockersamples/static-site
    ```
*   Para especificar uma porta:

    ```bash
      docker run -d -p 8080:80 dockersamples/static-site
    ```

> Notar que é p minúsculo para especificar a porta.

*   Para especificar variáveis de ambiente, usamos a flag `-e`:

    ```bash
      docker run -d -P -e AUTHOR="Eduardo" dockersamples/static-site
    ```

## Volumes

* Usados para persistir os dados, evitando que eles sejam perdidos quando o container morrer. Criar um volume significa criar uma pasta no container que indica que os arquivos dessa pasta não devem ser salvos na camada do container, mas sim no Docker Host.
* Para criar um volume no container, utilizamos a flag `-v` informando o nome da pasta que guardará os dados:

```bash
docker run -v "/var/www" ubuntu
```

* Podemos especificar onde (no nosso computador) esses dados serão salvos:

```bash
docker run -it -v "C:\Users\eduardo.cury\Desktop:/var/www" ubuntu
```

> Os arquivos criados na pasta /var/www do ubuntu serão escritos no Desktop.

## Criando uma imagem

* Para criar uma imagem, colocamos as instruções no **`Dockerfile`**.
* Usualmente, montamos uma imagem a partir de outra imagem base. Informamos a imagem base com o comando **`FROM`**.
* O comando **`MAINTAINER`** informa o dono da imagem.
* O comando **`COPY`** informa o código que queremos copiar para dentro da imagem.
* Para informar a pasta em que os comandos devem ser executados (_working directory_), nós usamos o comando **`WORKDIR`**.
* Usamos **`RUN`** para especificar um comando a ser executado na imagem.
* O comando **`ENTRYPOINT`** informa o comando a ser executado assim que o container for startado.
* Para indicar qual porta será exposta pela aplicação, usamos o comando **`EXPOSE`**

```bash
 FROM node:latest
 MAINTAINER Eduardo
 COPY . /var/www
 WORKDIR /var/www
 RUN npm install
 ENTRYPOINT ["npm", "start"]
 EXPOSE 3000
```

* Para criar a imagem, executamos o seguinte comando:

```bash
docker build -f Dockerfile -t eduardo/node .
```

A flag `-f` indica o nome do arquivo (se o arquivo se chamar Dockerfile, não preciso informar) e a flag `-t` indica a tag da imagem. Em seguida informarmos o local do arquivo (se estiver no _pwd_, colocamos um ponto).

## Comunicação entre contêineres

* Os containeres do Docker ficam em uma rede, e podem se comunicar entre si.
* A rede padrão criada pelo Docker não permite que os containeres se comuniquem através de seus nomes, apenas através de seus endereços IPs. Para isso, devemos criar uma nova rede:

```bash
docker network create --driver bridge minha-rede
```

* Agora, podemos criar contêineres que usam essa rede através da flag `--network`:

```bash
docker run -it --name meu-container --network minha-rede ubuntu
```

> O comando `hostname -i` mostra o endereço IP do container em que ele está sendo executado.

## Docker Compose

* Consegue gerenciar a execução de várias aplicações. Os detalhes são informados no arquivo **`docker-compose.yml`**.
* Cada container a ser subido é um `service`. Os `services` tem um nome associado dado por nós (mongodb, nginx, etc).
* Em `build`, nós especificamos o caminho para o `dockerfile` responsável para criar aquele container e o path até ele através do `context`.
* Também podemos especificar o nome da imagem (`image`), o nome do container (`container_name`), a(s) porta(s) expostas (`port`) e a rede do container (`networks`).
* Se o serviço em questão depender de outro container, especificamos em `depends_on`.
* Além de containers, podemos criar redes. Basta usar `networks`, informar o nome da rede a ser criada e o `driver`.

```yaml
version: '3'
 services:
     nginx:
         build:
             dockerfile: ./docker/nginx.dockerfile
             context: .
         image: eduardo/nginx
         container_name: nginx
         ports:
             - "80:80"
         networks:
             - production-network
         depends_on:
             - "node"
     mongodb:
         image: mongo
         networks:
             - production-network
     node:
         build:
             dockerfile: ./docker/alura-books.dockerfile
             context: .
         image: eduardo/alura-books
         container_name: alura-books1
         ports:
             - "3000"
         networks:
             - production-network
         depends_on:
             - "mongodb"
 networks:
     production-network:
         driver: bridge
```

* Para buildar as imagens a partir do arquivo:

```bash
docker-compose build
```

> Esse comando vai apenas criar as imagens.

* Para subir os containers, executamos:

```bash
docker-compose up
```

Em modo detatched:

```bash
docker-compose up -d
```

Também podemos ver os serviços sendo executados:

```bash
docker-compose ps
```

Para parar todos os containers (e deletá-los):

```bash
docker-compose down
```

## Comandos prontos

* Usando container do mysql:

```bash
docker run -p 3306:3306  --name mysql-container -e MYSQL_ROOT_PASSWORD=password -e MYSQL_USER=eduardo -e MYSQL_PASSWORD=password -d mysql:latest
```

* Usando mysql do host em um container:

```bash
docker container run -p 8080:8080 -e DB_NAME=host.docker.internal eduardorcury/proposta:1.0.1 --name proposta
```
