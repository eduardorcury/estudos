---
title: "Servlets"
layout: single
sidebar:
  nav: main
---

> Objeto que pode ser acessado pelo navegador e que gera respostas HTTP dinamicamente.

## Tomcat

* Servidor Web escrito em Java;
*   Para acessar arquivos _.html_ do projeto:

    ```
      http://localhost:8080/(NomeDoProjeto)/(NomeDoArquivo).html
    ```

    ```
      http://localhost:8080/gerenciador/bem-vindo.html
    ```
* É um servlet container, responsável por criar os servlets (_lazy_ por padrão);

## Criando Servlet

* Criar classe que herda de **`HttpServlet`**;
* Anotar com **`@WebServlet`**;
* Definir a URL em que o _Servlet_ deve ser chamado;

```java
@WebServlet(urlPatterns = "/oi")
public class OiMundoServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws 
                ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("Conteúdo do primeiro Servlet");
        out.println("</body>");
        out.println("</html>");

        System.out.println("Servlet OiMundo foi chamado");

    }

}
```

> O método sobrescrito _service_ consegue acessar a requisição e resposta HTTP.

## Métodos HTTP com Servlets

### Acessar parâmetros da URL

*   Método **`getParameter`** do objeto **`request`** é responsável por obter os parâmetros;

    ```java
      String nomeEmpresa = request.getParameter("nome");
    ```
*   Exemplo de URL:

    ```
      http://http://localhost:8080/gerenciador/novaEmpresa?nome=Alura
    ```

### POST VS GET

Em ambos os casos, o _servlet_ usa o método **`getParameter`** para obter os dados. No GET, os parâmetros são enviados na URL, enquanto no POST eles são enviados no _body_ da requisição.

### HTML do Form

```markup
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Insert title here</title>
    </head>

    <body>

        <form action="/gerenciador/novaEmpresa" method="post">
            Nome: <input type="text" name="nome" />
            <input type="submit">
        </form>

    </body>
</html>
```

### Métodos específicos

O método _service_ não restringe o tipo de método que deve ser utilizado na URL. Para fazer essa restrição, usamos os métodos:

1. **`doGet`**
2. **`doPost`**
3. **`doDelete`**
4.  **`doPut`**

    ...

Usar o método errado na URL retorna o erro HTTP **405**: _Method not allowed_.

## Java Server Pages

### Função

Possibilita executar código Java dentro do HTML (_scriplet_). Código Java deve estar contido em **`<% [...] %>`** e respeitar todas as regras da linguagem (ex: ponto e vírgula)

```markup
<%
    String nomeEmpresa = "Alura";
    System.out.println(nomeEmpresa);
%>

<html>

    <body>
        Empresa <%= nomeEmpresa %> cadastrada!
    </body>

</html>
```

### Enviando dados para a view

A forma de enviar dados para uma página _.jsp_ é chamar a página através de um objeto **`RequestDispatcher`**, adicionar um atributo à _request_ e enviar através do _dispatcher_ os objetos _request/response_.

```java
        RequestDispatcher dispatcher = request.getRequestDispatcher("/novaEmpresaCriada.jsp");
        request.setAttribute("empresa", empresa.getNome());
        dispatcher.forward(request, response);
```

### Recebendo dados na view

Para receber os dados na página, usa-se o método **`getAttribute`** e fazemos um casting no retorno.

```markup
<% String nomeEmpresa = (String) request.getAttribute("empresa"); %>

<html>
    <body>
        Empresa <%= nomeEmpresa %> cadastrada!
    </body>
</html>
```

Ou, com **Expression Language**:

```markup
<html>
    <body>
        Empresa ${ empresa } cadastrada!
    </body>
</html>
```

> Nota: aqui, a variável dentro de chaves deve ser igual ao atributo definido no Servlet.

### JSTL

É possível aumentar as funcionalidades do JSP com a biblioteca **JSTL** (_Java Standard Tag Lib_).

> Não esquecer de baixar a dependência da JSTL no projeto.

O import da biblioteca é feito no começo do arquivo:

```markup
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

1. For Loops

```markup
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Java Standard Tag Lib</title>
    </head>

    <body>

        Lista de empresas: <br>

        <ul>
            <c:forEach items="${lista}" var="empresa">
                <li>${ empresa.nome }</li>
            </c:forEach>
        </ul>

    </body>
</html>
```

> A expressão `empresa.nome` por baixo dos panos chamará o _getter_ do atributo. Se ele não existir, lançará um erro!

1. Contexto da aplicação

```markup
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/novaEmpresa" var="linkServletNovaEmpresa"/>

<!DOCTYPE html>
<html>
    <body>

        <form action="${ linkServletNovaEmpresa }" method="post">
            Nome: <input type="text" name="nome" />
            <input type="submit">
        </form>

    </body>
</html>
```

1. Condicional If

```markup
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <c:if test="${ not empty empresa }">
            Empresa ${ empresa } cadastrada!
        </c:if>
    </body>
</html>
```

1. Formatação

```markup
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <body>

        Lista de empresas: <br>

        <ul>
            <c:forEach items="${lista}" var="empresa">
                <li>${ empresa.nome } - 
                    <fmt:formatDate value="${ empresa.dataAbertura }" pattern="dd/MM/yyyy"/> 
                </li>
            </c:forEach>
        </ul>

    </body>
</html>
```

## Redirecionamento

*   Redirecionamento _server-side_: quando um _Servlet_ faz uma requisição a outro _Servlet_ a partir de um _dispatcher_;

    ```java
      RequestDispatcher dispatcher =     request.getRequestDispatcher("/listaEmpresas");
    ```
*   Redirecionamento _client-side_: o _Servlet_ usa o navegador como intermediário.

    ```java
      response.sendRedirect("listaEmpresas");
    ```

> Neste caso, a primeira requisição retornará um status code de **direcionamento** (301 ou 302).
>
> Como a requisição não é "passada" para o navegador, os atributos dela também não serão passados.
