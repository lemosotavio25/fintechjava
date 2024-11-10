// Função para calcular os totais de recebimentos e despesas
function calcularTotais() {
    let totalRecebimentos = 0;
    let totalDespesas = 0;

    // Calcula total de recebimentos
    document.querySelectorAll(".valor-recebimento").forEach((element) => {
        let valor = parseFloat(element.innerText.replace(",", "."));
        if (!isNaN(valor)) totalRecebimentos += valor;
    });

    // Calcula total de despesas
    document.querySelectorAll(".valor-despesa").forEach((element) => {
        let valor = parseFloat(element.innerText.replace(",", "."));
        if (!isNaN(valor)) totalDespesas += valor;
    });

    // Calcula saldo
    let saldo = totalRecebimentos - totalDespesas;

    // Exibe os resultados nos elementos HTML
    document.getElementById("total-recebimentos").innerText = totalRecebimentos.toFixed(2);
    document.getElementById("total-despesas").innerText = totalDespesas.toFixed(2);
    document.getElementById("saldo").innerText = saldo.toFixed(2);

    // Chama a função para criar o gráfico de pizza
    criarGraficoPizza(totalRecebimentos, totalDespesas);
}

// Função para criar o gráfico de pizza
function criarGraficoPizza(recebimentos, despesas) {
    const graficoContainer = document.getElementById('graficoContainer');
    if (recebimentos === 0 && despesas === 0) {
        graficoContainer.style.display = 'none';
        return;
    } else {
        graficoContainer.style.display = 'block';
    }

    const ctx = document.getElementById('graficoPizza').getContext('2d');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Despesas', 'Recebimentos'],
            datasets: [{
                data: [despesas, recebimentos],
                backgroundColor: ['#c0eaad', '#3d9a24'],
                hoverBackgroundColor: ['#c0eaad', '#3d9a24']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return tooltipItem.label + ': R$ ' + tooltipItem.raw.toFixed(2);
                        }
                    }
                }
            }
        }
    });
}

// Função para definir o mês atual no seletor ao carregar a página
function definirMesAtual() {
    const mesAtual = "${mesAtual}";
    document.getElementById('mesSelecionado').value = mesAtual;
}

// Função para redirecionar com o filtro de mês selecionado
function filtrarMes() {
    const mesSelecionado = document.getElementById('mesSelecionado').value;
    console.log("Mês selecionado:", mesSelecionado); // Log para depuração

    if (mesSelecionado) {
        const urlDestino = "dashboard?mes=" + mesSelecionado;
        console.log("Redirecionando para:", urlDestino); // Log para verificar a URL final
        window.location.href = urlDestino;
    } else {
        alert("Por favor, selecione um mês válido.");
    }
}

// Função de inicialização para configurar o mês atual e calcular totais ao carregar a página
window.onload = function() {
    definirMesAtual();
    calcularTotais();
};
