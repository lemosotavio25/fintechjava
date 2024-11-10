package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Recebimento;
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

@WebServlet(urlPatterns = {"/recebimento"})
public class RecebimentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer idConta = (Integer) session.getAttribute("idConta");

        // Pega os parâmetros do formulário
        String descricao = request.getParameter("descricao");
        double valor = Double.parseDouble(request.getParameter("valorRecebimento"));
        Date dataRecebimento = Date.valueOf(request.getParameter("dataRecebimento"));
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = "dashboard";
        }

        // Verifique se `idRecebimento` foi passado para identificar se é uma edição
        RecebimentoDao recebimentoDao = DaoFactory.getRecebimentoDao();
        try {
            String idRecebimentoStr = request.getParameter("idRecebimento");
            if (idRecebimentoStr != null && !idRecebimentoStr.isEmpty()) {
                int idRecebimento = Integer.parseInt(idRecebimentoStr);
                Recebimento recebimento = new Recebimento(idRecebimento, descricao, valor, dataRecebimento, idConta);
                recebimentoDao.atualizar(recebimento); // Atualiza o recebimento existente
            } else {
                Recebimento recebimento = new Recebimento(0, descricao, valor, dataRecebimento, idConta);
                recebimentoDao.cadastrar(recebimento); // Cadastra um novo recebimento
            }
            response.sendRedirect(returnUrl);
        } catch (DBException e) {
            e.printStackTrace();
            response.sendRedirect("erro.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String mes = request.getParameter("mes");
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = "dashboard";
        }

        HttpSession session = request.getSession(false);
        Integer idConta = (Integer) session.getAttribute("idConta");
        RecebimentoDao recebimentoDao = DaoFactory.getRecebimentoDao();

        if ("list".equals(action)) {
            List<Recebimento> recebimentos;
            if (mes != null && !mes.isEmpty()) {
                recebimentos = recebimentoDao.listarPorMes(idConta, mes);
            } else {
                recebimentos = recebimentoDao.listarRecebimentosPorConta(idConta);
            }
            request.setAttribute("recebimentos", recebimentos);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                recebimentoDao.remover(id);
                response.sendRedirect(returnUrl);
            } catch (DBException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Recebimento recebimento = recebimentoDao.buscar(id);
            request.setAttribute("recebimento", recebimento);
        }
    }
}