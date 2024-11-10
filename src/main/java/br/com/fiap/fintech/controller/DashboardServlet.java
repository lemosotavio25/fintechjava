package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.dao.ContaDao;
import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.dao.DespesaDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Conta;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer idConta = (Integer) session.getAttribute("idConta");
        String nomeUsuario = (String) session.getAttribute("nomeUsuario");

        if (idConta == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (nomeUsuario != null) {
            request.setAttribute("nomeUsuario", nomeUsuario);
        }

        // Obter conta
        ContaDao contaDao = DaoFactory.getContaDao();
        Conta conta = contaDao.buscar(idConta);
        request.setAttribute("conta", conta);

        // Obter o parâmetro de mês selecionado ou definir o mês atual
        String mes = request.getParameter("mes");
        if (mes == null || mes.isEmpty()) {
            mes = String.format("%02d", java.time.LocalDate.now().getMonthValue()); // Mês atual formatado como MM
        }
        request.setAttribute("mesAtual", mes); // Define o mês atual como atributo para ser acessado no JSP.

        // Carregar Recebimentos com o filtro de mês
        String urlRecebimento = "recebimento?action=list&mes=" + mes;
        request.getRequestDispatcher(urlRecebimento).include(request, response);

        // Carregar Despesas com o filtro de mês
        String urlDespesa = "despesa?action=list&mes=" + mes;
        request.getRequestDispatcher(urlDespesa).include(request, response);

        // Carregar Investimentos (sem filtro de mês, mantendo conforme está)
        request.getRequestDispatcher("investimento?action=list").include(request, response);

        RecebimentoDao recebimentoDao = DaoFactory.getRecebimentoDao();
        DespesaDao despesaDao = DaoFactory.getDespesaDao();

        // Carregar totais de recebimentos por mês
        Map<String, Double> totaisRecebimentosPorMes = recebimentoDao.listarTotalRecebimentosPorMes(idConta);
        request.setAttribute("totaisRecebimentosPorMes", totaisRecebimentosPorMes);

        // Carregar totais de despesas por mês
        Map<String, Double> totaisDespesasPorMes = despesaDao.listarTotaisDespesasPorMes(idConta);
        request.setAttribute("totaisDespesasPorMes", totaisDespesasPorMes);

        // Redirecionar para o dashboard.jsp
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);

    }
}
