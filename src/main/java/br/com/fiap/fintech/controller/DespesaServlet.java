package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.dao.DespesaDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Despesa;
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

@WebServlet("/despesa")
public class DespesaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer idConta = (Integer) session.getAttribute("idConta");

        // Pega os parâmetros do formulário
        String descricao = request.getParameter("descricao");
        double valor = Double.parseDouble(request.getParameter("valorDespesa"));
        Date dataDespesa = Date.valueOf(request.getParameter("dataDespesa"));

        // Verifica se um ID foi enviado para decidir entre criar ou editar
        String idParam = request.getParameter("idDespesa");
        int idDespesa = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

        // Define a URL de retorno com o mês, se presente
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = "dashboard";
        }

        DespesaDao despesaDao = DaoFactory.getDespesaDao();
        try {
            if (idDespesa > 0) {
                // Atualizar despesa existente
                Despesa despesaExistente = despesaDao.buscar(idDespesa);
                despesaExistente.setDescricao(descricao);
                despesaExistente.setValorDespesa(valor);
                despesaExistente.setDataDespesa(dataDespesa);
                despesaDao.atualizar(despesaExistente);
            } else {
                // Criar nova despesa
                Despesa despesa = new Despesa(0, descricao, valor, dataDespesa, idConta);
                despesaDao.cadastrar(despesa);
            }
            response.sendRedirect(returnUrl); // Redireciona para a URL original
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
        DespesaDao despesaDao = DaoFactory.getDespesaDao();

        if ("list".equals(action)) {
            List<Despesa> despesas;

            // Verifica se um mês foi selecionado para aplicar o filtro
            if (mes != null && !mes.isEmpty()) {
                despesas = despesaDao.listarPorMes(idConta, mes);
            } else {
                despesas = despesaDao.listarDespesasPorConta(idConta);
            }
            request.setAttribute("despesas", despesas);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                despesaDao.remover(id);
                response.sendRedirect(returnUrl);  // Redireciona para o returnUrl
            } catch (DBException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Despesa despesa = despesaDao.buscar(id);
            request.setAttribute("despesa", despesa);
        }
    }
}
