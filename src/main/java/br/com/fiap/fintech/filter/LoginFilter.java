package br.com.fiap.fintech.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String url = req.getRequestURI();

        // Verifica se a URL não requer login, como login, criar conta e recursos estáticos
        if (session.getAttribute("user") == null &&
                !url.endsWith("login") &&
                !url.endsWith("criarConta") && // Exceção para a página de criar conta
                !url.contains("resources") &&
                !url.contains("home")) {

            // Se não estiver logado e tentar acessar uma URL protegida, redireciona para home.jsp
            request.setAttribute("erro", "Entre com o usuário e senha!");
            request.getRequestDispatcher("home.jsp").forward(request, resp);
        } else {
            // Caso contrário, continua a requisição normalmente
            chain.doFilter(request, resp);
        }
    }
}
