<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Dasboard</title>
    <%@ include file="head.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100"> <!-- Flexbox aplicado ao body para layout flexível -->
<%@ include file="header.jsp" %> <!-- Incluindo o header.jsp aqui -->
<!-- Container com Coruja -->
<div class="container-coruja">
    <div class="d-flex">
        <img src="resources/images/coruja.png" class="icone_coruja" alt="Icon">
        <div class="speech-bubble">
            <p class="text-left fs-6 fw-bold">Eaí ${nomeUsuario}, bora controlar essas finanças!</p>
        </div>
    </div>
</div>
<!-- Indicadores Financeiros -->
<div class="container text-center mt-4">
    <div class="row">
        <div class="col-md-4 mb-3">
            <div class="card shadow">
                <div class="card-body d-flex align-items-center justify-content-center">
                    <p class="texto-indicadores mb-0">Total de recebimentos: R$</p>
                    <p class="texto-indicadores mb-0" id="total-recebimentos"></p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-3">
            <div class="card shadow">
                <div class="card-body d-flex align-items-center justify-content-center">
                    <p class="texto-indicadores mb-0">Total de despesas: R$</p>
                    <p class="texto-indicadores mb-0" id="total-despesas"></p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-3">
            <div class="card shadow">
                <div class="card-body d-flex align-items-center justify-content-center">
                    <p class="texto-indicadores mb-0">Saldo: R$</p>
                    <p class="texto-indicadores mb-0" id="saldo"></p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container" id="graficoContainer" style="width: 300px; height: 300px;">
    <canvas id="graficoPizza"></canvas>
</div>
<!-- Seleção de Mês -->
<div class="container text-center mt-4">
    <label for="mesSelecionado" class="form-label">Selecione o Mês: </label>
    <select id="mesSelecionado" class="form-select w-25 d-inline-block">
        <option value="01">Janeiro</option>
        <option value="02">Fevereiro</option>
        <option value="03">Março</option>
        <option value="04">Abril</option>
        <option value="05">Maio</option>
        <option value="06">Junho</option>
        <option value="07">Julho</option>
        <option value="08">Agosto</option>
        <option value="09">Setembro</option>
        <option value="10">Outubro</option>
        <option value="11">Novembro</option>
        <option value="12">Dezembro</option>
    </select>
    <button onclick="filtrarMes()" class="btn btn-primary ms-2">Filtrar</button>
</div>
<!-- Tabela de Entradas (Recebimentos) -->
<div class="container mt-5 p-3 shadow">
    <h4 class="text-left mx-2 mb-3 fw-bolder">Meus Recebimentos - ${mesAtual}/24</h4>
    <table id="tableEntradas" class="table table-responsive table-hover text-center table-sm table-borderless">
        <thead>
        <tr class="fundo-verde">
            <th>Descrição</th>
            <th>Valor</th>
            <th>Data</th>
            <th>Editar/Deletar</th>
        </tr>
        </thead>
        <tbody class="border">
        <!-- Usando JSTL para iterar sobre a lista de recebimentos -->
        <c:forEach var="recebimento" items="${recebimentos}">
            <tr>
                <td><c:out value="${recebimento.descricao}"/></td>
                <td><i class="fa fa-plus"></i> R$ <span class="valor-recebimento"><c:out value="${recebimento.valorRecebimento}"/></span></td>
                <td><c:out value="${recebimento.dataRecebimento}"/></td>
                <!-- Inside each row of your table, add the following -->
                <td>
                    <!-- Ícone de edição para recebimento com cor azul e tamanho maior -->
                    <a href="#modalEditarRecebimento${recebimento.idRecebimento}" data-bs-toggle="modal" class="text-info mx-2" style="text-decoration: none;">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                    </a>
                    <!-- Ícone de exclusão para recebimento com cor vermelha e tamanho maior -->
                    <a href="recebimento?action=delete&id=${recebimento.idRecebimento}&returnUrl=dashboard?mes=${param.mes}" class="text-danger mx-2" style="text-decoration: none;">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>
            <!-- Modal de Edição de Recebimento (dentro do loop) -->
            <div class="modal fade" id="modalEditarRecebimento${recebimento.idRecebimento}" tabindex="-1" aria-labelledby="modalEditarRecebimentoLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalEditarRecebimentoLabel">Editar Recebimento</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Formulário para edição com valores preenchidos -->
                            <form action="recebimento" method="post">
                                <input type="hidden" name="idRecebimento" value="${recebimento.idRecebimento}">
                                <input type="hidden" name="returnUrl" value="dashboard?mes=${param.mes}">
                                <div class="mb-3">
                                    <label for="inputDataEntrada${recebimento.idRecebimento}" class="form-label">Data</label>
                                    <input type="date" class="form-control" id="inputDataEntrada${recebimento.idRecebimento}" name="dataRecebimento" value="${recebimento.dataRecebimento}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputDescricaoEntrada${recebimento.idRecebimento}" class="form-label">Descrição</label>
                                    <input type="text" class="form-control" id="inputDescricaoEntrada${recebimento.idRecebimento}" name="descricao" value="${recebimento.descricao}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputValorEntrada${recebimento.idRecebimento}" class="form-label">Valor</label>
                                    <input type="number" class="form-control" id="inputValorEntrada${recebimento.idRecebimento}" name="valorRecebimento" value="${recebimento.valorRecebimento}" required>
                                </div>
                                <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </tbody>
    </table>
    <div class="container mt-0">
        <button type="button" class="btn fundo-verde mt-0" data-bs-toggle="modal" data-bs-target="#modalAdicionarEntrada">
            Adicionar Recebimento
        </button>
    </div>
</div>
<!-- Tabela de Saídas (Despesas) -->
<div class="container mt-5 p-3 shadow">
    <h4 class="text-left mx-2 mb-3 fw-bolder">Minhas Despesas - ${mesAtual}/24</h4>
    <table id="tableSaidas" class="table table-responsive table-hover text-center table-sm table-borderless">
        <thead>
        <tr class="fundo-verde-claro">
            <th>Descrição</th>
            <th>Valor</th>
            <th>Data</th>
            <th>Editar/Deletar</th>
        </tr>
        </thead>
        <tbody class="border">
        <!-- Usando JSTL para iterar sobre a lista de despesas -->
        <c:forEach var="despesa" items="${despesas}">
            <tr>
                <td><c:out value="${despesa.descricao}"/></td>
                <td><i class="fa fa-minus"></i> R$ <span class="valor-despesa"><c:out value="${despesa.valorDespesa}"/></span></td>
                <td><c:out value="${despesa.dataDespesa}"/></td>
                <!-- Inside each row of your table, add the following -->
                <td>
                    <!-- Ícone de edição com cor azul e tamanho maior -->
                    <a href="#modalEditarDespesa${despesa.idDespesa}" data-bs-toggle="modal" class="text-info mx-2" style="text-decoration: none;">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                    </a>

                    <!-- Ícone de exclusão com cor vermelha e tamanho maior -->
                    <a href="despesa?action=delete&id=${despesa.idDespesa}&returnUrl=dashboard?mes=${param.mes}" class="text-danger mx-2" style="text-decoration: none;">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>
            <!-- Modal de Edição de Despesa (dentro do loop) -->
            <div class="modal fade" id="modalEditarDespesa${despesa.idDespesa}" tabindex="-1" aria-labelledby="modalEditarDespesaLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalEditarDespesaLabel">Editar Despesa</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Formulário para edição com valores preenchidos -->
                            <!-- Formulário para edição com valores preenchidos -->
                            <form action="despesa" method="post">
                                <!-- Campo oculto para enviar o ID da despesa ao servlet -->
                                <input type="hidden" name="idDespesa" value="${despesa.idDespesa}">
                                <input type="hidden" name="returnUrl" value="dashboard?mes=${param.mes}">

                                <div class="mb-3">
                                    <label for="inputDataSaida${despesa.idDespesa}" class="form-label">Data</label>
                                    <input type="date" class="form-control" id="inputDataSaida${despesa.idDespesa}" name="dataDespesa" value="${despesa.dataDespesa}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputDescricaoSaida${despesa.idDespesa}" class="form-label">Descrição</label>
                                    <input type="text" class="form-control" id="inputDescricaoSaida${despesa.idDespesa}" name="descricao" value="${despesa.descricao}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputValorSaida${despesa.idDespesa}" class="form-label">Valor</label>
                                    <input type="number" class="form-control" id="inputValorSaida${despesa.idDespesa}" name="valorDespesa" value="${despesa.valorDespesa}" step="0.01" required>
                                </div>
                                <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </tbody>
    </table>
    <div class="container mt-0">
        <button type="button" class="btn fundo-verde-claro mt-0" data-bs-toggle="modal" data-bs-target="#modalAdicionarSaida">
            Adicionar Despesa
        </button>
    </div>
</div>
<!-- Tabela de Investimentos -->
<div class="container mt-5 p-3 shadow">
    <h4 class="text-left mx-2 mb-3 fw-bolder">Meus Investimentos - 2024</h4>
    <table id="tableInvestimentos" class="table table-responsive table-hover text-center table-sm table-borderless">
        <thead>
        <tr class="fundo-azul-claro">
            <th>Data de Investimento</th>
            <th>Tipo de Investimento</th>
            <th>Valor Investido</th>
            <th>Rentabilidade</th>
            <th>Data de Resgate</th>
            <th>Editar/Deletar</th>
        </tr>
        </thead>
        <tbody class="border">
        <!-- Usando JSTL para iterar sobre a lista de investimentos -->
        <c:forEach var="investimento" items="${investimentos}">
            <tr>
                <td><c:out value="${investimento.dataInvestimento}"/></td>
                <td><c:out value="${investimento.tipoInvestimento}"/></td>
                <td>R$ <span class="valor-investimento"><c:out value="${investimento.valorInvestido}"/></span></td>
                <td><c:out value="${investimento.rentabilidade}"/>%</td>
                <td><c:out value="${investimento.dataResgate}"/></td>
                <td>
                    <!-- Ícone de edição com cor azul e tamanho maior -->
                    <a href="#modalEditarInvestimento${investimento.idInvestimento}" data-bs-toggle="modal" class="text-info mx-2" style="text-decoration: none;">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                    </a>
                    <!-- Ícone de exclusão com cor vermelha e tamanho maior -->
                    <a href="investimento?action=delete&id=${investimento.idInvestimento}" class="text-danger mx-2" style="text-decoration: none;">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </a>
                </td>
            </tr>

            <!-- Modal de Edição de Investimento (dentro do loop) -->
            <div class="modal fade" id="modalEditarInvestimento${investimento.idInvestimento}" tabindex="-1" aria-labelledby="modalEditarInvestimentoLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalEditarInvestimentoLabel">Editar Investimento ${mesAtual}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Formulário para edição com valores preenchidos -->
                            <form action="investimento" method="post">
                                <input type="hidden" name="idInvestimento" value="${investimento.idInvestimento}">
                                <input type="hidden" name="mes" value="${mesAtual}">
                                <div class="mb-3">
                                    <label for="inputTipoInvestimento${investimento.idInvestimento}" class="form-label">Tipo de Investimento</label>
                                    <input type="text" class="form-control" id="inputTipoInvestimento${investimento.idInvestimento}" name="tipoInvestimento" value="${investimento.tipoInvestimento}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputValorInvestido${investimento.idInvestimento}" class="form-label">Valor Investido</label>
                                    <input type="number" class="form-control" id="inputValorInvestido${investimento.idInvestimento}" name="valorInvestido" value="${investimento.valorInvestido}" step="0.01" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputRentabilidade${investimento.idInvestimento}" class="form-label">Rentabilidade (%)</label>
                                    <input type="number" class="form-control" id="inputRentabilidade${investimento.idInvestimento}" name="rentabilidade" value="${investimento.rentabilidade}" step="0.01" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputDataInvestimento${investimento.idInvestimento}" class="form-label">Data de Investimento</label>
                                    <input type="date" class="form-control" id="inputDataInvestimento${investimento.idInvestimento}" name="dataInvestimento" value="${investimento.dataInvestimento}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="inputDataResgate${investimento.idInvestimento}" class="form-label">Data de Resgate</label>
                                    <input type="date" class="form-control" id="inputDataResgate${investimento.idInvestimento}" name="dataResgate" value="${investimento.dataResgate}" required>
                                </div>
                                <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </tbody>
    </table>
    <div class="container mt-0">
        <button type="button" class="btn fundo-azul-claro mt-0" data-bs-toggle="modal" data-bs-target="#modalAdicionarInvestimento">
            Adicionar Investimento
        </button>
    </div>
</div>
<!-- Modais de Adicionar Entrada, Saída, Investimentos -->
<!-- Modal Adicionar Entrada (Recebimentos) -->
<div class="modal fade" id="modalAdicionarEntrada" tabindex="-1" aria-labelledby="modalAdicionarEntradaLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalAdicionarEntradaLabel">Adicionar Novo Recebimento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="recebimento" method="post">
                    <input type="hidden" name="idRecebimento" value="${recebimento.idRecebimento}">
                    <input type="hidden" name="returnUrl" value="dashboard?mes=${param.mes}">
                    <div class="mb-3">
                        <label for="inputDataEntrada" class="form-label">Data</label>
                        <input type="date" class="form-control" id="inputDataEntrada" name="dataRecebimento" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputDescricaoEntrada" class="form-label">Descrição</label>
                        <input type="text" class="form-control" id="inputDescricaoEntrada" name="descricao" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputValorEntrada" class="form-label">Valor</label>
                        <input type="number" class="form-control" id="inputValorEntrada" name="valorRecebimento"  step="0.01" required>
                    </div>
                    <button type="submit" class="btn fundo-verde">Salvar</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal Adicionar Saída (Despesas) -->
<div class="modal fade" id="modalAdicionarSaida" tabindex="-1" aria-labelledby="modalAdicionarSaidaLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalAdicionarSaidaLabel">Adicionar Nova Despesa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="despesa" method="post"> <!-- Mude a ação para o Servlet que lidará com as despesas -->
                    <input type="hidden" name="returnUrl" value="dashboard?mes=${param.mes}">
                    <div class="mb-3">
                        <label for="inputDataSaida" class="form-label">Data</label>
                        <input type="date" class="form-control" id="inputDataSaida" name="dataDespesa" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputDescricaoSaida" class="form-label">Descrição</label>
                        <input type="text" class="form-control" id="inputDescricaoSaida" name="descricao" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputValorSaida" class="form-label">Valor</label>
                        <input type="number" class="form-control" id="inputValorSaida" name="valorDespesa"  step="0.01" required>
                    </div>
                    <button type="submit" class="btn fundo-verde">Salvar</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal Adicionar Investimento -->
<div class="modal fade" id="modalAdicionarInvestimento" tabindex="-1" aria-labelledby="modalAdicionarInvestimentoLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalAdicionarInvestimentoLabel">Adicionar Novo Investimento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="investimento" method="post"> <!-- Ação para o Servlet que lidará com os investimentos -->
                    <input type="hidden" name="mes" value="${mesAtual}">
                    <div class="mb-3">
                        <label for="inputTipoInvestimento" class="form-label">Tipo de Investimento</label>
                        <input type="text" class="form-control" id="inputTipoInvestimento" name="tipoInvestimento" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputValorInvestido" class="form-label">Valor Investido</label>
                        <input type="number" class="form-control" id="inputValorInvestido" name="valorInvestido" step="0.01" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputRentabilidade" class="form-label">Rentabilidade (%)</label>
                        <input type="number" class="form-control" id="inputRentabilidade" name="rentabilidade" step="0.01" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputDataInvestimento" class="form-label">Data de Investimento</label>
                        <input type="date" class="form-control" id="inputDataInvestimento" name="dataInvestimento" required>
                    </div>
                    <div class="mb-3">
                        <label for="inputDataResgate" class="form-label">Data de Resgate</label>
                        <input type="date" class="form-control" id="inputDataResgate" name="dataResgate">
                    </div>
                    <button type="submit" class="btn fundo-verde">Salvar</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Resumo Mensal - 2024 -->
<div class="container mt-5 p-3 shadow">
<h4 class="text-left mx-2 mb-3 fw-bolder">Resumo Mensal- 2024</h4>
<table class="table table-responsive table-hover text-center table-sm fomxp">
<thead style="color: white;">
<tr class="fundo-azul">
    <th style="">Mês</th>
    <th>Janeiro</th>
    <th>Fevereiro</th>
    <th>Março</th>
    <th>Abril</th>
    <th>Maio</th>
    <th>Junho</th>
    <th>Julho</th>
    <th>Agosto</th>
    <th>Setembro</th>
    <th>Outubro</th>
    <th>Novembro</th>
    <th>Dezembro</th>
</tr>
</thead>
<tbody>
<tr>
    <td class="fw-bold p-1">Entradas (R$)</td>
    <c:forEach var="entry" items="${totaisRecebimentosPorMes}">
        <td>R$ ${entry.value}</td>
    </c:forEach>
</tr>
<tr>
    <td class="fw-bold p-1">Saídas (R$)</td>
    <c:forEach var="entry" items="${totaisRecebimentosPorMes}">
        <td>R$ <c:out value="${totaisDespesasPorMes[entry.key] != null ? totaisDespesasPorMes[entry.key] : 0}" /></td>
    </c:forEach>
</tr>
<tr>
    <td class="fw-bold p-1">Saldo (R$)</td>
    <c:forEach var="entry" items="${totaisRecebimentosPorMes}">
        <c:set var="recebimento" value="${entry.value}" />
        <c:set var="despesa" value="${totaisDespesasPorMes[entry.key] != null ? totaisDespesasPorMes[entry.key] : 0}" />
        <td>R$ <c:out value="${recebimento - despesa}" /></td>
    </c:forEach>
</tr>
</tbody>
</table>
</div>
<!-- Resumo Mensal - 2024 -->
<div class="container mt-5 p-3 shadow">
    <h4 class="text-left mx-2 mb-3 fw-bolder">Resumo- 2024</h4>
    <canvas id="graficoResumo" width="400" height="200"></canvas>
</div>
<!-- Incluindo o footer.jsp -->
<%@ include file="footer.jsp" %>
<!-- Script para calcular totais -->
<script src="resources/js/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Pega os dados das variáveis JSTL
    const meses = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

    // Captura os recebimentos usando JSTL e gera um array de valores
    const recebimentos = [
        <c:forEach var="entry" items="${totaisRecebimentosPorMes}">
        ${entry.value},
        </c:forEach>
    ];

    // Captura as despesas usando JSTL e gera um array de valores
    const despesas = [
        <c:forEach var="entry" items="${totaisRecebimentosPorMes}">
        ${totaisDespesasPorMes[entry.key] != null ? totaisDespesasPorMes[entry.key] : 0},
        </c:forEach>
    ];

    // Calcula o saldo (recebimentos - despesas)
    const saldo = recebimentos.map((rec, i) => rec - despesas[i]);

    // Cria o gráfico usando Chart.js
    const ctx = document.getElementById('graficoResumo').getContext('2d');
    const graficoResumo = new Chart(ctx, {
        type: 'line',
        data: {
            labels: meses, // Eixo X com os meses
            datasets: [
                {
                    label: 'Entradas (R$)',
                    data: recebimentos,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    fill: false
                },
                {
                    label: 'Saídas (R$)',
                    data: despesas,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    fill: false
                },
                {
                    label: 'Saldo (R$)',
                    data: saldo,
                    borderColor: 'rgba(54, 162, 235, 1)',
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    fill: false
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
</body>
