package com.example.bolsointeligente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.dicas_investimentos.DicasInvestimentosAdapter;
import com.example.bolsointeligente.database.DicaInvestimento;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class InvestimentosActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private DicasInvestimentosAdapter dicaAdapter;
    private List<DicaInvestimento> listaDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.recyclerDicas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaDicas = new ArrayList<>();
        listaDicas.add(new DicaInvestimento("Diversificação de Carteira", "Distribua seus investimentos..."));
        listaDicas.add(new DicaInvestimento("Investimentos de Longo Prazo", "O poder dos juros compostos..."));
        listaDicas.add(new DicaInvestimento("Renda Fixa vs. Variável", "Entenda as diferenças..."));

        dicaAdapter = new DicasInvestimentosAdapter(listaDicas);
        recyclerView.setAdapter(dicaAdapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent intent = new Intent(InvestimentosActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(InvestimentosActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.Transacoes) {
                    Intent intent = new Intent(InvestimentosActivity.this, TransacoesActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(InvestimentosActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == R.id.simulador) {
                    Intent intent = new Intent(InvestimentosActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
    }
}