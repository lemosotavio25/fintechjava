package br.com.fiap.fintech.model;

import java.util.Date;

public class Investimento {
    private int idInvestimento;
    private String tipoInvestimento;
    private double valorInvestido;
    private double rentabilidade;
    private Date dataInvestimento;
    private Date dataResgate;
    private int idConta;

    // Construtores
    public Investimento() {}

    public Investimento(int idInvestimento, String tipoInvestimento, double valorInvestido, double rentabilidade, Date dataInvestimento, Date dataResgate, int idConta) {
        this.idInvestimento = idInvestimento;
        this.tipoInvestimento = tipoInvestimento;
        this.valorInvestido = valorInvestido;
        this.rentabilidade = rentabilidade;
        this.dataInvestimento = dataInvestimento;
        this.dataResgate = dataResgate;
        this.idConta = idConta;
    }

    // Getters e Setters
    public int getIdInvestimento() {
        return idInvestimento;
    }

    public void setIdInvestimento(int idInvestimento) {
        this.idInvestimento = idInvestimento;
    }

    public String getTipoInvestimento() {
        return tipoInvestimento;
    }

    public void setTipoInvestimento(String tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    public double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public double getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(double rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public Date getDataInvestimento() {
        return dataInvestimento;
    }

    public void setDataInvestimento(Date dataInvestimento) {
        this.dataInvestimento = dataInvestimento;
    }

    public Date getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(Date dataResgate) {
        this.dataResgate = dataResgate;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    @Override
    public String toString() {
        return "Investimento{" +
                "idInvestimento=" + idInvestimento +
                ", tipoInvestimento='" + tipoInvestimento + '\'' +
                ", valorInvestido=" + valorInvestido +
                ", rentabilidade=" + rentabilidade +
                ", dataInvestimento=" + dataInvestimento +
                ", dataResgate=" + dataResgate +
                ", idConta=" + idConta +
                '}';
    }
}
