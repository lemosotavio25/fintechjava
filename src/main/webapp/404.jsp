<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Error - 404</title>
    <%@ include file="head.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100"> <!-- Flexbox aplicado ao body para layout flexível -->
<%@ include file="header.jsp" %> <!-- Incluindo o header.jsp aqui -->

<main class="flex-grow-1 container mt-4 mb-5"> <!-- Conteúdo principal com flex-grow para ocupar espaço -->
    <div class="container-coruja">
        <div class="d-flex">
            <img src="resources/images/coruja.png" class="icone_coruja" alt="Icon">
            <div class="speech-bubble">
                <p class="text-left fs-6 fw-bold">Ops! Página não encontrada!</p>
            </div>
        </div>
    </div>
    <div class="flex-grow-1 container mt-5">
        <div class="container-coruja">
            <div class="d-flex">
                <h1>Erro - 404</h1>
            </div>
        </div>
    </div>
</main>

<%@ include file="footer.jsp" %> <!-- Incluindo o footer.jsp -->
</body>
</html>
