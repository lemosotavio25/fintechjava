package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.dao.UsuarioDao;
import br.com.fiap.fintech.dao.ContaDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.model.Conta;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.util.CriptografiaUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/criarConta")
public class CriarContaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtendo dados do formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String banco = request.getParameter("banco");
        String tipoConta = request.getParameter("tipoConta");

        // Criptografando a senha antes de armazenar
        String senhaCriptografada = null;
        try {
            senhaCriptografada = CriptografiaUtils.criptografar(senha);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erroCadastro", "Erro ao criptografar a senha.");
            request.getRequestDispatcher("criarConta.jsp").forward(request, response);
            return;
        }

        UsuarioDao usuarioDao = DaoFactory.getUsuarioDao();
        ContaDao contaDao = DaoFactory.getContaDao();

        try {
            // Verifica se já existe um usuário com o mesmo email
            Usuario usuarioExistente = usuarioDao.buscarPorEmail(email);
            if (usuarioExistente != null) {
                request.setAttribute("erroCadastro", "Email já cadastrado.");
                request.getRequestDispatcher("criarConta.jsp").forward(request, response);
                return;
            }

            // Criando o novo Usuário
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senhaCriptografada);

            usuarioDao.cadastrar(usuario);

            // Buscando o ID do usuário recém-criado
            Usuario usuarioCriado = usuarioDao.buscarPorEmail(email);

            // Criando a Conta associada ao usuário
            Conta conta = new Conta();
            conta.setBanco(banco);
            conta.setTipoConta(tipoConta);
            conta.setIdUsuario(usuarioCriado.getId());

            contaDao.cadastrar(conta);

            // Busca o ID da conta recém-criada para configurar na sessão
            Conta contaCriada = contaDao.buscarPorIdUsuario(usuarioCriado.getId());

            // Login automático com configuração do ID da conta na sessão
            HttpSession session = request.getSession();
            session.setAttribute("user", usuarioCriado);
            session.setAttribute("usuarioLogado", email);
            session.setAttribute("idConta", contaCriada.getIdConta());
            response.sendRedirect("dashboard");

        } catch (DBException e) {
            e.printStackTrace();
            request.setAttribute("erroCadastro", e.getMessage());
            request.getRequestDispatcher("criarConta.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("criarConta.jsp").forward(request, response);
    }
}
