package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.impl.OracleInvestimentoDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Investimento;

import java.util.Date;
import java.util.List;

public class InvestimentoTeste {

    public static void main(String[] args) {
        // Criar uma instância da DAO
        OracleInvestimentoDao investimentoDao = new OracleInvestimentoDao();

        // Criar um novo investimento para a conta com ID 4
        Investimento novoInvestimento = new Investimento();
        novoInvestimento.setTipoInvestimento("Ações");
        novoInvestimento.setValorInvestido(5000.00);
        novoInvestimento.setRentabilidade(7.5);
        novoInvestimento.setDataInvestimento(new Date()); // Data de hoje como exemplo
        novoInvestimento.setDataResgate(new Date()); // Exemplo, mesma data para fins de teste (normalmente seria uma data futura)
        novoInvestimento.setIdConta(4); // ID da conta ao qual o investimento será vinculado

        try {
            // Cadastrar o novo investimento
            investimentoDao.cadastrar(novoInvestimento);
            System.out.println("Investimento cadastrado com sucesso!");

        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar investimento.");
        }

        // Listar todos os investimentos cadastrados
        try {
            List<Investimento> listaInvestimentos = investimentoDao.listar();
            System.out.println("Lista de Investimentos Cadastrados:");
            for (Investimento investimento : listaInvestimentos) {
                System.out.println(investimento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar investimentos.");
        }
    }
}
