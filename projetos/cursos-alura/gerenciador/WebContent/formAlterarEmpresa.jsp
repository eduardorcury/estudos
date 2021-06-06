<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/alterarEmpresa" var="linkServletAlterarEmpresa"/>

<!DOCTYPE html>
<html>
	
	<body>
	
		<form action="${ linkServletAlterarEmpresa }" method="post">
		
			Nome: <input type="text" name="nome" value="${ empresa.nome }" />
			
			Data Abertura: <input type="text" name="data" value="<fmt:formatDate value="${ empresa.dataAbertura }" pattern="dd/MM/yyyy"/>" />
		
			<input type="hidden" name="id" value="${ empresa.id }">
			
			<input type="submit">
		
		</form>
	
	</body>
</html>