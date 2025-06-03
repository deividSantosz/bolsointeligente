package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Spinner;

import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.R;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.database.UsuarioDao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;

import com.github.mikephil.charting.charts.BarChart; // Import para BarChart
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter; // Para formatar eixo X do BarChart
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class EstatisticasActivity extends AppCompatActivity {
    private BarChart barChart;
    private PieChart pieChart;
    BottomNavigationView bottomNavigationView;
    Database db;
    private int usuarioId;
    private TransacaoDao transacaoDao;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas); // Certifique-se que este é o layout com ConstraintLayout como pai
        db = Room.databaseBuilder(getApplicationContext(), com.example.bolsointeligente.database.Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // Lembre-se: idealmente, mova para background thread
                .build();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        pieChart = findViewById(R.id.pie_chart_despesas_categoria);
        barChart = findViewById(R.id.bar_chart_receitas_despesas);

        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        transacaoDao = db.transacaoDao();
        usuarioDao = db.usuarioDao();
        setupPieChart();
        setupBarChart();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.estatisticas) {
                    return true;
                } else if (itemId == R.id.home) {
                    Intent intent = new Intent(EstatisticasActivity.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish(); // Finaliza a EstatisticasActivity
                    return true;
                } else if (itemId == R.id.simulador) {
                    Intent intent = new Intent(EstatisticasActivity.this, SimulacaoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish(); // Finaliza a EstatisticasActivity
                    return true;
                } else if (itemId == R.id.perfil) {
                    Intent intent = new Intent(EstatisticasActivity.this, PerfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish(); // Finaliza a EstatisticasActivity
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.estatisticas);
    }

    private void setupPieChart() {
        List<Transacao> todasTransacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);

        if (todasTransacoes == null || todasTransacoes.isEmpty()) {
            pieChart.clear();
            pieChart.setCenterText("Sem transações para exibir");
            pieChart.invalidate();
            return;
        }

        Map<String, Double> despesasPorNomeCategoria = new HashMap<>();
        for (Transacao transacao : todasTransacoes) {
            if (transacao.getValor() < 0) {
                double valorDespesa = Math.abs(transacao.getValor());
                String nomeCategoriaDaTransacao = transacao.getCategoria(); // Supondo que getCategoria() retorna a String

                if (nomeCategoriaDaTransacao == null || nomeCategoriaDaTransacao.trim().isEmpty()) {
                    nomeCategoriaDaTransacao = "Outros";
                }
                despesasPorNomeCategoria.put(nomeCategoriaDaTransacao,
                        despesasPorNomeCategoria.getOrDefault(nomeCategoriaDaTransacao, 0.0) + valorDespesa);
            }
        }

        if (despesasPorNomeCategoria.isEmpty()) {
            pieChart.clear();
            pieChart.setCenterText("Sem despesas registradas");
            pieChart.invalidate();
            return;
        }

        List<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : despesasPorNomeCategoria.entrySet()) {
            if (entry.getValue() > 0) {
                pieEntries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
            }
        }

        if (pieEntries.isEmpty()){
            pieChart.clear();
            pieChart.setCenterText("Sem despesas para categorizar");
            pieChart.invalidate();
            return;
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");

        Map<String, Integer> coresDasCategorias = new HashMap<>();
        coresDasCategorias.put("Alimentação", Color.parseColor("#FF0000"));
        coresDasCategorias.put("Saúde", Color.parseColor("#368FF4"));
        coresDasCategorias.put("Animal", Color.parseColor("#0057FF"));
        coresDasCategorias.put("Lazer", Color.parseColor("#8109CC"));
        coresDasCategorias.put("Educação", Color.parseColor("#FF9800"));
        coresDasCategorias.put("Investimento", Color.parseColor("#128E03"));
        coresDasCategorias.put("Transporte", Color.parseColor("#FF0066"));
        coresDasCategorias.put("Outros", Color.parseColor("#757575"));

        ArrayList<Integer> coresParaOGrafico = new ArrayList<>();
        for (PieEntry entry : pieEntries) {
            String nomeCategoria = entry.getLabel();
            // Pega a cor definida para a categoria, ou uma cor padrão se a categoria não estiver no mapa
            // (útil se uma nova categoria aparecer nas transações antes de ser adicionada ao mapa de cores)
            coresParaOGrafico.add(coresDasCategorias.getOrDefault(nomeCategoria, Color.LTGRAY));
        }
        dataSet.setColors(coresParaOGrafico);

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(11f);
        dataSet.setSliceSpace(2f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.4f);
        dataSet.setValueLinePart2Length(0.6f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new com.github.mikephil.charting.formatter.PercentFormatter(pieChart));
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.DKGRAY);

        pieChart.setDrawEntryLabels(false);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE); // Para combinar com as "bolinhas" da sua imagem
        legend.setTextSize(11f); // Ajuste conforme necessário
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setYEntrySpace(5f); // Espaço vertical entre itens da legenda se quebrar linha
        legend.setXEntrySpace(10f); // Espaço horizontal entre itens da legenda

        pieChart.setExtraOffsets(5.f, 10.f, 5.f, legend.mNeededHeight + 5f); // Ajusta offset inferior para a legenda
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
    private void setupBarChart() {
        Double rendaMensalDouble = null;
        if (db != null) {

            if (usuarioDao != null) {
                rendaMensalDouble = usuarioDao.getUserRenda(usuarioId);
            } else {
                Log.e("EstatisticasActivity", "UsuarioDao é NULO ao tentar obter renda.");

            }
        } else {
            Log.e("EstatisticasActivity", "Database (db) é NULO ao tentar obter UsuarioDao.");
        }

        float rendaMensalParaGrafico = 0f;
        if (rendaMensalDouble != null) {
            rendaMensalParaGrafico = rendaMensalDouble.floatValue();
        }


        List<Transacao> todasTransacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);
        if (todasTransacoes == null || todasTransacoes.isEmpty() && rendaMensalParaGrafico <= 0) {
            barChart.clear();
            barChart.setNoDataText("Sem dados para exibir.");
            barChart.invalidate();
            return;
        }

        // 2. Processar Transações para obter Despesas Mensais
        Map<String, Double> despesasPorMes = new TreeMap<>(); // TreeMap para ordenar por chave (mês/ano)
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM/yy", new Locale("pt", "BR"));
        Calendar cal = Calendar.getInstance();

        if (todasTransacoes != null) {
            for (Transacao t : todasTransacoes) {
                // Supondo que Transacao tem um campo 'data' do tipo long (timestamp)
                if (t.getData() == 0) continue; // Pular transações sem data válida
                cal.setTimeInMillis(t.getData());
                String mesAno = monthYearFormat.format(cal.getTime());

                if (t.getValor() < 0) { // Apenas despesas
                    despesasPorMes.put(mesAno, despesasPorMes.getOrDefault(mesAno, 0.0) + Math.abs(t.getValor()));
                }
            }
        }

        ArrayList<BarEntry> entriesRenda = new ArrayList<>();
        ArrayList<BarEntry> entriesDespesas = new ArrayList<>();
        final ArrayList<String> xAxisLabels = new ArrayList<>();

        int index = 0;

        // Define o período para o gráfico (ex: últimos 6 meses, ou baseado no spinner)
        // Para este exemplo, vamos usar os meses onde houve despesas, ou os últimos X meses se não houver despesas mas houver renda.
        Map<String, Boolean> mesesConsiderados = new TreeMap<>();
        if (!despesasPorMes.isEmpty()) {
            for (String mes : despesasPorMes.keySet()) {
                mesesConsiderados.put(mes, true);
            }
        } else if (rendaMensalParaGrafico > 0) { // Se não há despesas, mas há renda, mostre para alguns meses recentes
            Calendar tempCal = Calendar.getInstance();
            for (int i = 5; i >= 0; i--) { // Ex: últimos 6 meses (incluindo o atual)
                tempCal.setTime(Calendar.getInstance().getTime());
                tempCal.add(Calendar.MONTH, -i);
                mesesConsiderados.put(monthYearFormat.format(tempCal.getTime()), true);
            }
        }
        for (String mesAno : mesesConsiderados.keySet()) {
            xAxisLabels.add(mesAno.substring(0, 3)); // Usar apenas as 3 primeiras letras do mês para o label
            float despesaDoMes = despesasPorMes.getOrDefault(mesAno, 0.0).floatValue();

            entriesRenda.add(new BarEntry(index, rendaMensalParaGrafico));
            entriesDespesas.add(new BarEntry(index, despesaDoMes));
            index++;
        }


        if (entriesRenda.isEmpty() && entriesDespesas.isEmpty()) {
            barChart.clear();
            barChart.setNoDataText("Sem dados para exibir.");
            barChart.invalidate();
            return;
        }

        BarDataSet setRenda = new BarDataSet(entriesRenda, "Renda");
        setRenda.setColor(Color.parseColor("#3498DB")); // Azul para Renda
        setRenda.setValueTextSize(10f);
        setRenda.setDrawValues(false);

        BarDataSet setDespesas = new BarDataSet(entriesDespesas, "Despesas");
        setDespesas.setColor(Color.parseColor("#E74C3C")); // Vermelho para Despesas
        setDespesas.setValueTextSize(10f);
        setDespesas.setDrawValues(true);


        // Configuração para barras agrupadas
        float groupSpace = 0.14f;
        float barSpace = 0.03f; // x2 DataSets
        float barWidth = 0.40f;  // x2 DataSets
        BarData data = new BarData(setRenda, setDespesas);
        data.setBarWidth(barWidth);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);

        if (index > 0) { // Só agrupa e formata eixos se houver dados
            // O ponto de partida para groupBars é o primeiro grupo.
            // As labels do XAxis precisam corresponder ao centro dos grupos.
            barChart.groupBars(0f, groupSpace, barSpace);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setCenterAxisLabels(true); // Centraliza os rótulos entre as barras do grupo
            xAxis.setAxisMinimum(0f);
            // O máximo do eixo X deve ser o número de grupos (index)
            // A largura total que os grupos ocuparão é data.getGroupWidth(groupSpace, barSpace) * index
            // Para que o groupBars funcione corretamente e o último grupo seja visível,
            // o AxisMaximum deve ser igual ao número de entradas (grupos)
            xAxis.setAxisMaximum(index);
            // xAxis.setLabelCount(index, false); // Força o número de labels

            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setAxisMinimum(0f); // Garante que o eixo Y comece em 0
            leftAxis.setSpaceTop(15f); // Adiciona um pouco de espaço no topo

            barChart.getAxisRight().setEnabled(false);
            barChart.getLegend().setEnabled(true);
            barChart.setDrawGridBackground(false);
            barChart.setFitBars(true); // Tenta fazer as barras se encaixarem bem
            barChart.animateY(1000);
        } else {
            barChart.clear(); // Limpa se não houver dados para os grupos
            barChart.setNoDataText("Sem dados suficientes para os meses selecionados.");
        }
        barChart.invalidate(); // Atualiza o gráfico
    }

}