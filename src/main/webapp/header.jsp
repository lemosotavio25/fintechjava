<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- Inclusão da tag JSTL -->

<!-- header.jsp -->
<nav class="navbar bg-body-tertiary border">
    <div class="container-fluid">
        <!-- Logo ou link para a página inicial -->
        <a class="navbar-brand mx-3" href="dashboard">
            <i class="fas fa-glasses"></i>Budget Supervisor
        </a>

        <!-- Verificação de sessão e exibição do botão "Sair" -->
        <c:if test="${not empty sessionScope.usuarioLogado}">
            <form action="LogoutServlet" method="post" class="d-inline">
                <button type="submit" class="btn fundo-verde border text-light mt-2">Sair</button>
            </form>
        </c:if>
    </div>
</nav>
