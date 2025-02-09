package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Transacao;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EstatisticasActivity extends AppCompatActivity {
    private LineChart lineChart;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<Transacao> listaTransacoes = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.rv_transactions_estatisticas);
        lineChart = findViewById(R.id.line_chart);

        setupLineChart();

        // Configurar a lista de transações
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Adicione um adapter aqui para as transações

        listaTransacoes.add(new Transacao(1, - 150.0, "Pagamento de salário", "Alimentação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(2, -50.0, "Compra supermercado", "Educação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(3, 200.0, "Venda de item", "Outros", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(4, -30.0, "Almoço", "Investimentos", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -100.0, "Pagamento de contas", "Saúde", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -80.0, "Pagamento de contas", "Transporte", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -100.0, "Pagamento de contas", "Beleza", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -500, "Pagamento de contas", "Lazer", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -67, "Pagamento de contas", "Animal", System.currentTimeMillis(), 1));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(null);

        ListaTransacoesAdapter adapter = new ListaTransacoesAdapter(listaTransacoes, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setupLineChart();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(EstatisticasActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.Transacoes) {
                    Intent intent = new Intent(EstatisticasActivity.this, TransacoesActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(EstatisticasActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
    }
    private void setupLineChart() {
        // Criar entradas para o gráfico
        List<Entry> entries = new ArrayList<>();

        // Log para verificar os valores das transações
        for (int i = 0; i < listaTransacoes.size(); i++) {
            Transacao transacao = listaTransacoes.get(i);
            Log.d("Transacao", "Index: " + i + " Valor: " + transacao.getValor());
            entries.add(new Entry(i, (float) transacao.getValor()));
        }

        // Criar o dataset com as entradas
        LineDataSet dataSet = new LineDataSet(entries, "Transactions");
        dataSet.setColor(Color.parseColor("#1ABC9C"));
        dataSet.setCircleColor(Color.parseColor("#1ABC9C"));
        dataSet.setLineWidth(2f);
        dataSet.setValueTextColor(Color.TRANSPARENT);

        // Criar os dados do gráfico
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Configurações do eixo X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Garantir que o eixo X tenha valores visíveis
        xAxis.setLabelCount(entries.size(), true); // Ajusta a quantidade de rótulos
        xAxis.setDrawGridLines(false);

        // Configurações do eixo Y
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Outras configurações
        lineChart.getDescription().setText("Gráfico de Transações");
        lineChart.getDescription().setEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.invalidate(); // Atualizar o gráfico
    }

}
