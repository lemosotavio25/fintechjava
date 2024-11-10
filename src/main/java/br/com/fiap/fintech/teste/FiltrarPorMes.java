package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Recebimento;

import java.util.List;

public class FiltrarPorMes {
    public static void main(String[] args) {
        RecebimentoDao recebimentoDao = DaoFactory.getRecebimentoDao();

        int idConta = 4;  // Suponha que o ID da conta é 1 para o teste
        String mes = "10";  // Janeiro, por exemplo

        try {
            List<Recebimento> listaRecebimentos = recebimentoDao.listarPorMes(idConta, mes);
            System.out.println("Lista de Recebimentos Cadastrados no Mês de Janeiro:");
            for (Recebimento recebimento : listaRecebimentos) {
                System.out.println(recebimento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar recebimentos para o mês especificado.");
        }
    }
}
