package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.impl.OracleDespesaDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Despesa;

import java.util.Date;
import java.util.List;

public class DespesaTeste {

    public static void main(String[] args) {
        // Criar uma instância da DAO
        OracleDespesaDao despesaDao = new OracleDespesaDao();

        // Criar uma nova despesa para a conta com ID 4
        Despesa novaDespesa = new Despesa();
        novaDespesa.setDescricao("Aluguel");
        novaDespesa.setValorDespesa(1200.00);
        novaDespesa.setDataDespesa(new Date()); // Data de hoje como exemplo
        novaDespesa.setIdConta(4); // ID da conta ao qual a despesa será vinculada

        try {
            // Cadastrar a nova despesa
            despesaDao.cadastrar(novaDespesa);
            System.out.println("Despesa cadastrada com sucesso!");

        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar despesa.");
        }

        // Listar todas as despesas cadastradas
        try {
            List<Despesa> listaDespesas = despesaDao.listar();
            System.out.println("Lista de Despesas Cadastradas:");
            for (Despesa despesa : listaDespesas) {
                System.out.println(despesa);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar despesas.");
        }
    }
}
