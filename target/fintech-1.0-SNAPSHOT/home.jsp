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
<div class="container-coruja">
  <div class="d-flex">
    <img src="resources/images/coruja.png" class="icone_coruja" alt="Icon">
    <div class="speech-bubble">
      <p class="text-left fs-6 fw-bold">Por favor faça o login!</p>
    </div>
  </div>
</div>
<div class="row justify-content-center">
  <div class="col-md-6">
    <h2 class="text-center"></h2>

    <!-- Formulário de login -->
    <form action="login" method="post">
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" name="email" placeholder="Digite seu email" value="teste@fiap.com" required>
      </div>
      <div class="mb-3">
        <label for="senha" class="form-label">Senha</label>
        <input type="password" class="form-control" id="senha" name="senha" placeholder="Digite sua senha" value="123456" required>
      </div>
      <div class="d-grid gap-2">
        <button type="submit" class="btn btn-success">Entrar</button>
      </div>
    </form>

    <!-- Opção para criar conta -->
    <div class="mt-3 text-center">
      <p>Não tem uma conta?</p>
      <a href="criarConta" class="btn btn-primary">Criar Conta</a>
    </div>
  </div>
</div>

</main>

<%@ include file="footer.jsp" %> <!-- Incluindo o footer.jsp -->
</body>
</html>
