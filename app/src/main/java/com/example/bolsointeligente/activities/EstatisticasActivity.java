package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
// Remova a importação da Toolbar se não estiver usando a MaterialToolbar do XML
// import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog; // Import para DatePickerDialog
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater; // Import para LayoutInflater
import android.view.MenuItem;
import android.view.View; // Import para View
import android.widget.DatePicker; // Já importado
import android.widget.ImageView;
import android.widget.TextView; // Import para TextView
import android.widget.Toast; // Import para Toast

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.database.UsuarioDao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;

import com.github.mikephil.charting.charts.BarChart;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter; // Import para PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate; // Import para ColorTemplate
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
    private ImageView iconefiltro;

    private Calendar dataInicioSelecionada = Calendar.getInstance();
    private Calendar dataFimSelecionada = Calendar.getInstance();
    private SimpleDateFormat formatoDataView = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        db = Room.databaseBuilder(getApplicationContext(), com.example.bolsointeligente.database.Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        pieChart = findViewById(R.id.pie_chart_despesas_categoria);
        barChart = findViewById(R.id.bar_chart_receitas_despesas);
        iconefiltro = findViewById(R.id.iconefiltro);

        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        transacaoDao = db.transacaoDao();
        usuarioDao = db.usuarioDao();

        carregarGraficosComTodosOsDados();


        iconefiltro.setOnClickListener(v -> mostrarDialogoDeFiltroIntervaloDatas());

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
                    finish();
                    return true;
                } else if (itemId == R.id.simulador) {
                    Intent intent = new Intent(EstatisticasActivity.this, SimulacaoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.perfil) {
                    Intent intent = new Intent(EstatisticasActivity.this, PerfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.estatisticas);
    }

    private void carregarGraficosComTodosOsDados() {
        List<Transacao> todasTransacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);
        long dataMin = Long.MAX_VALUE;
        long dataMax = 0L;
        if (todasTransacoes != null && !todasTransacoes.isEmpty()) {
            for (Transacao t : todasTransacoes) {
                if(t.getData() == 0) continue;
                if (t.getData() < dataMin) dataMin = t.getData();
                if (t.getData() > dataMax) dataMax = t.getData();
            }
        } else {
            dataMin = 0L;
            dataMax = System.currentTimeMillis();
        }

        setupPieChartComDados(todasTransacoes);
        setupBarChartComDados(todasTransacoes, dataMin, dataMax);
    }
    private void mostrarDialogoDeFiltroIntervaloDatas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        // Lembre-se de criar este arquivo de layout: res/layout/dialog_filtro_intervalo_datas.xml
        View dialogView = inflater.inflate(R.layout.dialog_filtro_intervalo_datas, null);
        builder.setView(dialogView);

        final TextView tvDataInicioDialog = dialogView.findViewById(R.id.tv_data_inicio_filtro);
        final TextView tvDataFimDialog = dialogView.findViewById(R.id.tv_data_fim_filtro);

        Calendar tempCalInicio = (Calendar) dataInicioSelecionada.clone();
        Calendar tempCalFim = (Calendar) dataFimSelecionada.clone();

        tvDataInicioDialog.setText(formatoDataView.format(tempCalInicio.getTime()));
        tvDataFimDialog.setText(formatoDataView.format(tempCalFim.getTime()));

        tvDataInicioDialog.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        tempCalInicio.set(year, month, dayOfMonth);
                        tvDataInicioDialog.setText(formatoDataView.format(tempCalInicio.getTime()));
                    },
                    tempCalInicio.get(Calendar.YEAR),
                    tempCalInicio.get(Calendar.MONTH),
                    tempCalInicio.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            if (tempCalFim != null) {
                datePickerDialog.getDatePicker().setMaxDate(tempCalFim.getTimeInMillis());
            }
            datePickerDialog.show();
        });

        tvDataFimDialog.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        tempCalFim.set(year, month, dayOfMonth);
                        tvDataFimDialog.setText(formatoDataView.format(tempCalFim.getTime()));
                    },
                    tempCalFim.get(Calendar.YEAR),
                    tempCalFim.get(Calendar.MONTH),
                    tempCalFim.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            if (tempCalInicio != null) {
                datePickerDialog.getDatePicker().setMinDate(tempCalInicio.getTimeInMillis());
            }
            datePickerDialog.show();
        });

        builder.setTitle("Filtrar por Período");
        builder.setPositiveButton("Aplicar", (dialog, which) -> {
            if (tempCalFim.before(tempCalInicio)) {
                Toast.makeText(this, "Data final não pode ser anterior à data inicial.", Toast.LENGTH_LONG).show();
                return;
            }
            dataInicioSelecionada = (Calendar) tempCalInicio.clone();
            dataFimSelecionada = (Calendar) tempCalFim.clone();

            dataInicioSelecionada.set(Calendar.HOUR_OF_DAY, 0); dataInicioSelecionada.set(Calendar.MINUTE, 0); dataInicioSelecionada.set(Calendar.SECOND, 0);
            dataFimSelecionada.set(Calendar.HOUR_OF_DAY, 23); dataFimSelecionada.set(Calendar.MINUTE, 59); dataFimSelecionada.set(Calendar.SECOND, 59);

            atualizarGraficosComBaseNoPeriodo(dataInicioSelecionada.getTimeInMillis(), dataFimSelecionada.getTimeInMillis());
        });
        builder.setNegativeButton("Cancelar", null);
        builder.setNeutralButton("Mostrar Todos", (dialog, which) -> {
            Toast.makeText(this, "Exibindo todas as transações", Toast.LENGTH_SHORT).show();
            carregarGraficosComTodosOsDados();
        });

        builder.create().show();
    }

    private void atualizarGraficosComBaseNoPeriodo(long dataInicioMillis, long dataFimMillis) {
        Log.d("EstatisticasActivity", "Filtrando de: " + formatoDataView.format(dataInicioMillis) + " ate " + formatoDataView.format(dataFimMillis));
        List<Transacao> transacoesFiltradas = transacaoDao.listarTransacoesPorUsuarioEPeriodo(usuarioId, dataInicioMillis, dataFimMillis);

        setupPieChartComDados(transacoesFiltradas);
        setupBarChartComDados(transacoesFiltradas, dataInicioMillis, dataFimMillis);
    }

    private void setupPieChartComDados(List<Transacao> transacoesDoPeriodo) {
        if (transacoesDoPeriodo == null || transacoesDoPeriodo.isEmpty()) {
            pieChart.clear();
            pieChart.invalidate();
            return;
        }

        Map<String, Double> despesasPorNomeCategoria = new HashMap<>();
        for (Transacao transacao : transacoesDoPeriodo) {
            if (transacao.getValor() < 0) {
                double valorDespesa = Math.abs(transacao.getValor());
                String nomeCategoriaDaTransacao = transacao.getCategoria();
                if (nomeCategoriaDaTransacao == null || nomeCategoriaDaTransacao.trim().isEmpty()) {
                    nomeCategoriaDaTransacao = "Outros";
                }
                despesasPorNomeCategoria.put(nomeCategoriaDaTransacao,
                        despesasPorNomeCategoria.getOrDefault(nomeCategoriaDaTransacao, 0.0) + valorDespesa);
            }
        }

        if (despesasPorNomeCategoria.isEmpty()) {
            pieChart.clear();
            pieChart.setCenterText("Sem despesas para categorizar");
            pieChart.invalidate();
            return;
        }

        List<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : despesasPorNomeCategoria.entrySet()) {
            if (entry.getValue() > 0) {
                pieEntries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
            }
        }

        if (pieEntries.isEmpty()) {
            pieChart.clear();
            pieChart.setCenterText("Sem dados de despesa");
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
            coresParaOGrafico.add(coresDasCategorias.getOrDefault(entry.getLabel(), Color.LTGRAY));
        }
        if (!coresParaOGrafico.isEmpty()) { dataSet.setColors(coresParaOGrafico); }

        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setSliceSpace(2f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.DKGRAY);
        pieChart.setDrawEntryLabels(false);

        pieChart.setExtraOffsets(20.f, 5.f, 20.f, 5.f);

        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(11f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setYEntrySpace(5f);
        legend.setXEntrySpace(10f);

        pieChart.animateY(1000);
        pieChart.invalidate();
    }
    private void setupBarChartComDados(List<Transacao> transacoesDoPeriodo, long dataInicioPeriodo, long dataFimPeriodo) {
        Double rendaMensalDouble = null;
        if (usuarioDao != null) { // usuarioDao já foi inicializado em onCreate
            rendaMensalDouble = usuarioDao.getUserRenda(usuarioId);
        }

        float rendaMensalParaGrafico = 0f;
        if (rendaMensalDouble != null) {
            rendaMensalParaGrafico = rendaMensalDouble.floatValue();
        }

        if ((transacoesDoPeriodo == null || transacoesDoPeriodo.isEmpty()) && rendaMensalParaGrafico <= 0) {
            barChart.clear();
            barChart.setNoDataText("Sem dados para exibir.");
            barChart.invalidate();
            return;
        }

        Map<String, Double> despesasPorMes = new TreeMap<>();
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM/yy", new Locale("pt", "BR"));
        Calendar calIter = Calendar.getInstance();

        if (transacoesDoPeriodo != null) {
            for (Transacao t : transacoesDoPeriodo) {
                if (t.getData() == 0) continue;
                calIter.setTimeInMillis(t.getData());
                String mesAno = monthYearFormat.format(calIter.getTime());
                if (t.getValor() < 0) {
                    despesasPorMes.put(mesAno, despesasPorMes.getOrDefault(mesAno, 0.0) + Math.abs(t.getValor()));
                }
            }
        }

        ArrayList<BarEntry> entriesRenda = new ArrayList<>();
        ArrayList<BarEntry> entriesDespesas = new ArrayList<>();
        final ArrayList<String> xAxisLabels = new ArrayList<>();
        int index = 0;

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTimeInMillis(dataInicioPeriodo);
        Calendar calFim = Calendar.getInstance();
        calFim.setTimeInMillis(dataFimPeriodo);

        calInicio.set(Calendar.DAY_OF_MONTH, 1);

        while (!calInicio.after(calFim)) {
            String mesAnoLabel = monthYearFormat.format(calInicio.getTime());
            xAxisLabels.add(mesAnoLabel.substring(0, 3)); // Ex: "Mai"

            float despesaDoMes = despesasPorMes.getOrDefault(mesAnoLabel, 0.0).floatValue();
            entriesRenda.add(new BarEntry(index, rendaMensalParaGrafico));
            entriesDespesas.add(new BarEntry(index, despesaDoMes));
            index++;
            calInicio.add(Calendar.MONTH, 1); // Próximo mês
        }


        if (entriesRenda.isEmpty() && entriesDespesas.isEmpty()) {
            barChart.clear();
            barChart.setNoDataText("Sem dados para exibir para o período.");
            barChart.invalidate();
            return;
        }

        BarDataSet setRenda = new BarDataSet(entriesRenda, "Renda");
        setRenda.setColor(Color.parseColor("#3498DB"));
        setRenda.setValueTextSize(10f);
        setRenda.setDrawValues(false);

        BarDataSet setDespesas = new BarDataSet(entriesDespesas, "Despesas");
        setDespesas.setColor(Color.parseColor("#E74C3C"));
        setDespesas.setValueTextSize(10f);
        setDespesas.setDrawValues(true);

        float groupSpace = 0.14f;
        float barSpace = 0.03f;
        float barWidth = 0.40f;
        BarData data = new BarData(setRenda, setDespesas);
        data.setBarWidth(barWidth);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);

        if (index > 0) {
            barChart.groupBars(0f, groupSpace, barSpace);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setCenterAxisLabels(true);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(index);


            YAxis leftAxis = barChart.getAxisLeft();
            leftAxis.setAxisMinimum(0f);
            leftAxis.setSpaceTop(15f);
            barChart.getAxisRight().setEnabled(false);
            barChart.getLegend().setEnabled(true);
            barChart.setDrawGridBackground(false);
            barChart.setFitBars(true);
            barChart.animateY(1000);
        } else {
            barChart.clear();
            barChart.setNoDataText("Sem dados para os meses do período.");
        }
        barChart.invalidate();
    }
}