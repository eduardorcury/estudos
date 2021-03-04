# Servlets

> Objeto que pode ser acessado pelo navegador e que gera respostas HTTP dinamicamente.

## Tomcat

- Servidor Web escrito em Java;
- Para acessar arquivos *.html* do projeto:
	```
	http://localhost:8080/(NomeDoProjeto)/(NomeDoArquivo).html
	```
	```
	http://localhost:8080/gerenciador/bem-vindo.html
	```
## Criando Servlet

- Criar classe que herda de **`HttpServlet`**;
- Anotar com **`@WebServlet`**;
- Definir a URL em que o *Servlet* deve ser chamado;

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
> O método sobrescrito *service* consegue acessar a requisição e resposta HTTP.

## Métodos HTTP com Servlets

### Acessar parâmetros da URL

- Método **`getParameter`** do objeto **`request`** é responsável por obter os parâmetros;

	```java
	String nomeEmpresa = request.getParameter("nome");
	```
- Exemplo de URL:

	```
	http://http://localhost:8080/gerenciador/novaEmpresa?nome=Alura
	```

### POST VS GET

Em ambos os casos, o *servlet* usa o método **`getParameter`** para obter os dados. No GET, os parâmetros são enviados na URL, enquanto no POST eles são enviados no *body* da requisição.

### HTML do Form

```html
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

O método *service* não restringe o tipo de método que deve ser utilizado na URL. Para fazer essa restrição, usamos os métodos:

1. **`doGet`**
2. **`doPost`**
3. **`doDelete`**
4. **`doPut`**
	...

Usar o método errado na URL retorna o erro HTTP **405**: *Method not allowed*.

## Java Server Pages

### Função

Possibilita executar código Java dentro do HTML (*scriplet*).
Código Java deve estar contido em **`<% [...] %>`** e respeitar todas as regras da linguagem (ex: ponto e vírgula)

```html
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

A forma de enviar dados para uma página *.jsp* é chamar a página através de um objeto **`RequestDispatcher`**, adicionar um atributo à *request* e enviar através do *dispatcher* os objetos *request/response*.

	```java
		RequestDispatcher dispatcher = request.getRequestDispatcher("/novaEmpresaCriada.jsp");
		request.setAttribute("empresa", empresa.getNome());
		dispatcher.forward(request, response);
	```

### Recebendo dados na view

Para receber os dados na página, usa-se o método **`getAttribute`** e fazemos um casting no retorno.

```html
<% String nomeEmpresa = (String) request.getAttribute("empresa"); %>

<html>
	<body>
		Empresa <%= nomeEmpresa %> cadastrada!
	</body>
</html>
```

Ou, com **Expression Language**:

```html
<html>
	<body>
		Empresa ${ empresa } cadastrada!
	</body>
</html>
```

> Nota: aqui, a variável dentro de chaves deve ser igual ao atributo definido no Servlet.

### JSTL

É possível aumentar as funcionalidades do JSP com a biblioteca **JSTL** (*Java Standard Tag Lib*).

> Não esquecer de baixar a dependência da JSTL no projeto.

O import da biblioteca é feito no começo do arquivo:

```html 
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

1. For Loops

```html
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

> A expressão `empresa.nome` por baixo dos panos chamará o *getter* do atributo. Se ele não existir, lançará um erro!

2. Contexto da aplicação

```html
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

3. Condicional If

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<body>
		<c:if test="${ not empty empresa }">
			Empresa ${ empresa } cadastrada!
		</c:if>
	</body>
</html>
```

4. Formatação

```html
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

- Redirecionamento *server-side*: quando um *Servlet* faz uma requisição a outro *Servlet* a partir de um *dispatcher*;

	```java 
	RequestDispatcher dispatcher = 	request.getRequestDispatcher("/listaEmpresas");
	```
	
- Redirecionamento *client-side*: o *Servlet* usa o navegador como intermediário.

	```java 
	response.sendRedirect("listaEmpresas");
	```
	
> Neste caso, a primeira requisição retornará um status code de **direcionamento** (301 ou 302).

> Como a requisição não é "passada" para o navegador, os atributos dela também não serão passados.

## PUT & DELETE

> Perguntar sobre PUT
> Ele não usou doDelete???
> Input hidden nao funciona XDDDDD
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3NDc0NTE5NjgsLTE1NDExNjI3MzUsLT
czMTUzMDM1NCwxMDE1MzIxODc2LDE3MjM4NzQ0NTksLTI5MjY1
MzY0OSwtMTg4MTA2NDU1OCwxMDg5MTY1NTIyLDk3NjYwMjYxMi
wtMTQ5Mzg4MjAwNywtODY1NjMwNzU4LDE4NDg4NzY0MTYsNDIw
NjYyNjc0XX0=
-->