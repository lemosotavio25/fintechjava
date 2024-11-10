package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.dao.UsuarioDao;
import br.com.fiap.fintech.dao.ContaDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.model.Conta;
import br.com.fiap.fintech.util.CriptografiaUtils;  // Importa a classe de criptografia

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtém os parâmetros do formulário de login
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Criptografa a senha
        try {
            senha = CriptografiaUtils.criptografar(senha); // Aplica a criptografia
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erroLogin", "Erro ao criptografar a senha.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Valida o usuário utilizando o DAO
        UsuarioDao usuarioDao = DaoFactory.getUsuarioDao();
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);  // Define a senha criptografada

        boolean loginValido = false;
        try {
            loginValido = usuarioDao.validarUsuario(usuario);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if (loginValido) {
            try {
                // Recupera o usuário completo para obter o ID
                usuario = usuarioDao.buscarPorEmail(email);

                // Recupera a conta associada ao usuário
                ContaDao contaDao = DaoFactory.getContaDao();
                Conta conta = contaDao.buscarPorIdUsuario(usuario.getId());

                // Verifica se a conta foi encontrada
                if (conta != null) {
                    // Login bem-sucedido: cria uma sessão e armazena os dados
                    HttpSession session = request.getSession();
                    session.setAttribute("user", usuario);
                    session.setAttribute("usuarioLogado", email);
                    session.setAttribute("nomeUsuario", usuario.getNome());  // Armazena o nome
                    session.setAttribute("idConta", conta.getIdConta());

                    // Redireciona para o dashboard
                    response.sendRedirect("dashboard");
                } else {
                    // Caso o usuário não tenha conta associada
                    request.setAttribute("erroLogin", "Conta não encontrada.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } catch (DBException e) {
                // Tratar a exceção DBException
                e.printStackTrace();
                request.setAttribute("erroLogin", "Erro no banco de dados.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            // Credenciais inválidas: exibe mensagem de erro no login.jsp
            request.setAttribute("erroLogin", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona para o login.jsp
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
