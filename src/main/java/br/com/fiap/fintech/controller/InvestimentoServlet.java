package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.dao.InvestimentoDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Investimento;
import br.com.fiap.fintech.exception.DBException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/investimento")
public class InvestimentoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer idConta = (Integer) session.getAttribute("idConta");
        String mes = request.getParameter("mes");

        // Pega os parâmetros do formulário
        String tipoInvestimento = request.getParameter("tipoInvestimento");
        double valorInvestido = Double.parseDouble(request.getParameter("valorInvestido"));
        double rentabilidade = Double.parseDouble(request.getParameter("rentabilidade"));
        Date dataInvestimento = Date.valueOf(request.getParameter("dataInvestimento"));
        Date dataResgate = request.getParameter("dataResgate") != null ? Date.valueOf(request.getParameter("dataResgate")) : null;

        // Verifica se é edição ou criação
        String idInvestimentoStr = request.getParameter("idInvestimento");
        InvestimentoDao investimentoDao = DaoFactory.getInvestimentoDao();

        try {
            if (idInvestimentoStr != null && !idInvestimentoStr.isEmpty()) {
                // Edição
                int idInvestimento = Integer.parseInt(idInvestimentoStr);
                Investimento investimento = new Investimento(idInvestimento, tipoInvestimento, valorInvestido, rentabilidade, dataInvestimento, dataResgate, idConta);
                investimentoDao.atualizar(investimento);
            } else {
                // Criação
                Investimento investimento = new Investimento(0, tipoInvestimento, valorInvestido, rentabilidade, dataInvestimento, dataResgate, idConta);
                investimentoDao.cadastrar(investimento);
            }
            response.sendRedirect("dashboard?mes=" + mes);  // Redireciona para o mês correto

        } catch (DBException e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp");  // Redireciona para uma página de erro em caso de exceção
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        Integer idConta = (Integer) session.getAttribute("idConta");
        String mes = request.getParameter("mes");

        InvestimentoDao investimentoDao = DaoFactory.getInvestimentoDao();

        if ("list".equals(action)) {
            // Lista os investimentos da conta e os atribui à request para exibição
            List<Investimento> investimentos = investimentoDao.listarPorConta(idConta);
            request.setAttribute("investimentos", investimentos);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                investimentoDao.remover(id);
                response.sendRedirect("dashboard?mes=" + mes);
            } catch (DBException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Investimento investimento = investimentoDao.buscar(id);
            request.setAttribute("investimento", investimento);
        }
    }
}
