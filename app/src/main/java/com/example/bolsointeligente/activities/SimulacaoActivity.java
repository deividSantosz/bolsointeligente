package com.example.bolsointeligente.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulacaoActivity extends AppCompatActivity {

    private EditText etValorInicial, etTempo;
    private Spinner spTipoInvestimento;
    private Button btnSimular;
    private LineChart chartProjecao;
    private TextView tvResultado;
    Map<String, Double> taxas = new HashMap<>();

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

        String[] tiposInvestimento = {"Escolha a categoria de investimento", "Poupança", "CDB", "Ações"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposInvestimento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoInvestimento.setAdapter(adapter);

        taxas.put("Poupança", 5.0); // 5% ao ano
        taxas.put("CDB", 8.0); // 8% ao ano
        taxas.put("Ações", 15.0); // 15% ao ano
        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simularInvestimento();
            }
        });

        // Configurar o gráfico
        configurarGrafico();
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
        for (int i = 1; i <= 5; i++) { // Exemplo: 5 anos com valor inicial 0
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
        tvResultado.setText(String.format("Resultado final: R$ %.2f", valorFinal));
        tvResultado.setTextSize(20);
    }


}
