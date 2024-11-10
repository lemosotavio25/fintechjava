<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Login - Fintech Budget Supervisor</title>
    <%@ include file="head.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100"> <!-- Flexbox aplicado ao body para layout flexível -->
<%@ include file="header.jsp" %> <!-- Incluindo o header.jsp aqui -->

<main class="flex-grow-1 container mt-4"> <!-- Conteúdo principal com flex-grow para ocupar espaço -->
    <h2>Login</h2>
    <form action="login" method="post">
        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" name="email" class="form-control" value="teste@fiap.com" required>
        </div>
        <div class="mb-3">
            <label for="senha" class="form-label">Senha:</label>
            <input type="password" name="senha" class="form-control" value="123456" required>
        </div>
        <button type="submit" class="btn btn-primary">Entrar</button>
    </form>
</main>



    <!-- Exibe a mensagem de erro se houver -->
    <c:if test="${not empty erroLogin}">
        <p style="color: red;">${erroLogin}</p>
    </c:if>
</main>

<%@ include file="footer.jsp" %> <!-- Incluindo o footer.jsp -->
</body>
</html>
