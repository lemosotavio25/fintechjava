<!DOCTYPE html>
<html lang="pt-br">
<head>
<title>Criar Conta</title>
<%@ include file="head.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="header.jsp" %> <!-- Incluindo o header.jsp aqui -->
<style>
    /* Centralizando o form e o conteúdo */
    .container {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        min-height: 80vh; /* Ocupa a altura da tela */
    }

    form {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 10px; /* Espaçamento entre os elementos */
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 10px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        background-color: #f9f9f9;
    }

    form input, form button {
        width: 100%;
        max-width: 300px; /* Largura máxima dos inputs */
        padding: 10px;
        margin-top: 10px;
    }

    form h2 {
        margin-bottom: 20px;
    }

    button {
        background-color: #28a745;
        color: white;
        border: none;
        cursor: pointer;
    }

    button:hover {
        background-color: #218838;
    }

    .error-message {
        color: red;
        font-weight: bold;
        margin-bottom: 20px;
    }
</style>
<main class="flex-grow-1 container mt-4">
<div class="container mt-5 mb-5">
    <h2>Criar Conta</h2>
    <!-- Exibe a mensagem de erro, se existir -->
    <%
        String erroCadastro = (String) request.getAttribute("erroCadastro");
        if (erroCadastro != null) {
    %>
    <div class="error-message">
        <%= erroCadastro %>
    </div>
    <%
        }
    %>
    <form action="criarConta" method="post">
        <!-- Dados do Usuário -->
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required>

        <!-- Dados da Conta -->
        <label for="banco">Banco:</label>
        <input type="text" id="banco" name="banco" required>

        <label for="tipoConta">Tipo de Conta:</label>
        <input type="text" id="tipoConta" name="tipoConta" required>

        <button type="submit">Criar Conta</button>
    </form>
</div>
</main>
<%@ include file="footer.jsp" %> <!-- Incluindo o footer.jsp -->
</body>
</html>
