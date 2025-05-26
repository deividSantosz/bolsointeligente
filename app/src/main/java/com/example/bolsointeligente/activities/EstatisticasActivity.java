package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class EstatisticasActivity extends AppCompatActivity {
    private LineChart lineChart;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<Transacao> listaTransacoes = new ArrayList<>();
    ListaTransacoesAdapter adapter;
    Database db;
    private int usuarioId;
    private TransacaoDao transacaoDao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);
        db = Room.databaseBuilder(getApplicationContext(), com.example.bolsointeligente.database.Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.rv_transactions_estatisticas);
        lineChart = findViewById(R.id.line_chart);

        setupLineChart();

        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        transacaoDao = db.transacaoDao();
        adapter = new ListaTransacoesAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        carregarTransacoes();

        setupLineChart();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.estatisticas) {
                    return true;
                }
                else if (itemId == R.id.home) {
                    Intent intent = new Intent(EstatisticasActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (itemId == R.id.simulador) {
                    Intent intent = new Intent(EstatisticasActivity.this, SimulacaoActivity.class);

                    startActivity(intent);
                    return true;
                }
                else if (itemId == R.id.perfil) {
                    Intent intent = new Intent(EstatisticasActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.estatisticas);
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
    private void carregarTransacoes() {
        List<Transacao> transacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);
        if (transacoes != null && !transacoes.isEmpty()) {
            adapter.listaTransacoes.clear();
            adapter.listaTransacoes.addAll(transacoes);
            adapter.notifyDataSetChanged();
        }
    }
}
