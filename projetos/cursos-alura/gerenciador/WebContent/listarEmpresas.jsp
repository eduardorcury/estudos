<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/removerEmpresa" var="linkServletDeletarEmpresa"/>
<c:url value="/mostrarEmpresa" var="linkServletMostrarEmpresa"/>

<!DOCTYPE html>

<html>

	<body>
	
		<c:if test="${ not empty empresa }">
			Empresa ${ empresa } cadastrada!
		</c:if>
	
		Lista de empresas: <br>
		
		<ul>
			<c:forEach items="${lista}" var="empresa">
				<li>
					${ empresa.nome } - <fmt:formatDate value="${ empresa.dataAbertura }" pattern="dd/MM/yyyy"/> 
					<a href="${ linkServletMostrarEmpresa }?id=${ empresa.id }">Atualizar</a>
					<a href="${ linkServletDeletarEmpresa }?id=${ empresa.id }">Remover</a>	
				</li>
			</c:forEach>
		</ul>
		
	</body>

</html>