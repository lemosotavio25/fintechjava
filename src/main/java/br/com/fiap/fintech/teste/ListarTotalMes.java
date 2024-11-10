package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.dao.DespesaDao;
import br.com.fiap.fintech.factory.DaoFactory;
import java.util.Map;

public class ListarTotalMes {

    public static void main(String[] args) {
        // IDs de conta para o teste
        int idConta = 23; // Utilize o ID de uma conta válida no seu banco de dados

        // Teste para Recebimentos agrupados por mês
        RecebimentoDao recebimentoDao = DaoFactory.getRecebimentoDao();
        Map<String, Double> totaisRecebimentos = recebimentoDao.listarTotalRecebimentosPorMes(idConta);

        System.out.println("Totais de Recebimentos por Mês:");
        for (Map.Entry<String, Double> entry : totaisRecebimentos.entrySet()) {
            String mes = entry.getKey();
            Double total = entry.getValue();
            System.out.printf("Mês: %s - Total Recebido: R$ %.2f%n", mes, total);
        }

        // Teste para Despesas agrupadas por mês
        DespesaDao despesaDao = DaoFactory.getDespesaDao();
        Map<String, Double> totaisDespesas = despesaDao.listarTotaisDespesasPorMes(idConta);

        System.out.println("\nTotais de Despesas por Mês:");
        for (Map.Entry<String, Double> entry : totaisDespesas.entrySet()) {
            String mes = entry.getKey();
            Double total = entry.getValue();
            System.out.printf("Mês: %s - Total Gasto: R$ %.2f%n", mes, total);
        }
    }
}
