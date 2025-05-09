package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolsointeligente.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulacaoActivity extends AppCompatActivity {

    private EditText etValorInicial, etTempo;
    private Spinner spTipoInvestimento;
    private Button btnSimular;
    private LineChart chartProjecao;
    private TextView tvResultado, txtCliqueAqui, txtvalorfinal;
    BottomNavigationView bottomNavigationView;
    Map<String, Double> taxas = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulacao);

        // Inicializar os componentes
        etValorInicial = findViewById(R.id.et_valor_inicial);
        etTempo = findViewById(R.id.et_tempo);
        spTipoInvestimento = findViewById(R.id.sp_tipo_investimento);
        btnSimular = findViewById(R.id.btn_simular);
        chartProjecao = findViewById(R.id.chart_projecao);
        tvResultado = findViewById(R.id.tv_resultado);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        txtCliqueAqui = findViewById(R.id.txt_cliqueaqui);
        txtvalorfinal = findViewById(R.id.txt_valorfinal);
        tvResultado.setVisibility(View.GONE);
        txtvalorfinal.setVisibility(View.GONE);


        String[] tiposInvestimento = {"Escolha a categoria de investimento", "Poupança", "CDB"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposInvestimento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoInvestimento.setAdapter(adapter);
        taxas.put("Poupança", 6.17); // Taxa real de poupança em 2025 (~6.17% a.a.)
        taxas.put("CDB", 12.0);      // Média estimada de CDB pré-fixado em 2025 (~12% a.a.)

        configurarGrafico();
        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simularInvestimento();
            }
        });

        txtCliqueAqui.setOnClickListener((View view ) -> {
            Intent intent = new Intent(SimulacaoActivity.this, InvestimentosActivity.class);
            startActivity(intent);
            finish();
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(SimulacaoActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(SimulacaoActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.home) {
                    Intent intent = new Intent(SimulacaoActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.simulador) {
                    Intent intent = new Intent(SimulacaoActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
    }

    private void configurarGrafico() {
        chartProjecao.getDescription().setEnabled(false);
        chartProjecao.setTouchEnabled(true);
        chartProjecao.setPinchZoom(true);

        XAxis xAxis = chartProjecao.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chartProjecao.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        chartProjecao.getAxisRight().setEnabled(false);

        // Criar dados padrão (zeros)
        List<Entry> entradasIniciais = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            entradasIniciais.add(new Entry(i, 0));
        }

        LineDataSet dataSet = new LineDataSet(entradasIniciais, "Projeção inicial");
        dataSet.setColor(getColor(R.color.primaria));
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(getColor(R.color.primaria));
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        chartProjecao.setData(lineData);
        chartProjecao.invalidate(); // Atualizar o gráfico para mostrar os dados padrão
    }


    private void simularInvestimento() {
        // Obter o valor inicial e tempo inseridos pelo usuário
        String valorInicialStr = etValorInicial.getText().toString();
        String tempoStr = etTempo.getText().toString();

        // Validar os campos
        if (valorInicialStr.isEmpty() || tempoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorInicial = Double.parseDouble(valorInicialStr);
        int tempo = Integer.parseInt(tempoStr);

        // Obter a taxa de juros com base no tipo de investimento
        String tipoInvestimento = spTipoInvestimento.getSelectedItem().toString();
        if (!taxas.containsKey(tipoInvestimento)) {
            Toast.makeText(this, "Selecione um tipo de investimento válido!", Toast.LENGTH_SHORT).show();
            return;
        }

        double taxaJuros = taxas.get(tipoInvestimento); // Obter a taxa automaticamente

        // Calcular o investimento com juros compostos
        List<Entry> entradasGrafico = new ArrayList<>();
        double valorFinal = valorInicial;

        for (int i = 1; i <= tempo; i++) {
            valorFinal = valorInicial * Math.pow((1 + (taxaJuros / 100)), i);
            entradasGrafico.add(new Entry(i, (float) valorFinal));
        }

        // Atualizar o gráfico
        LineDataSet dataSet = new LineDataSet(entradasGrafico, "Projeção de retorno por ano.");
        dataSet.setColor(getColor(R.color.primaria));
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(getColor(R.color.primaria));
        dataSet.setCircleRadius(7f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        chartProjecao.setData(lineData);
        chartProjecao.invalidate(); // Atualizar o gráfico

        // Exibir o resultado final
        txtvalorfinal.setText(String.format("Valor final: R$ %.2f", valorFinal));
        txtvalorfinal.setTextSize(20);

        tvResultado.setVisibility(View.VISIBLE);
        txtvalorfinal.setVisibility(View.VISIBLE);

    }


}
