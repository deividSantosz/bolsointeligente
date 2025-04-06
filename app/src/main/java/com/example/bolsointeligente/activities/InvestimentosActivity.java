package com.example.bolsointeligente.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.DicasInvestimentos.DicasInvestimentosAdapter;
import com.example.bolsointeligente.activities.Acoes.AcaoAdapter;
import com.example.bolsointeligente.activities.RendaFixa.RendaFixaAdapter;
import com.example.bolsointeligente.database.Acao;
import com.example.bolsointeligente.database.DicaInvestimento;
import com.example.bolsointeligente.database.RendaFixa;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class InvestimentosActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private TextView  txtCliqueAqui;
    private RecyclerView recylerViewDica, recyclerViewAcao, recyclerViewRendaFixa;
    private DicasInvestimentosAdapter dicaAdapter;
    private AcaoAdapter acaoAdapter;
    private RendaFixaAdapter rendaFixaAdapter;
    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        txtCliqueAqui = findViewById(R.id.txt_cliqueaqui);
        tabLayout = findViewById(R.id.tabLayout);

        recyclerViewAcao = findViewById(R.id.recycleviewAcao);
        recylerViewDica = findViewById(R.id.recyclerDicas);
        recyclerViewRendaFixa = findViewById(R.id.recyclerViewRendaFixa);
        recylerViewDica.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAcao.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRendaFixa.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRendaFixa.setVisibility(View.GONE);
        dicaAdapter = new DicasInvestimentosAdapter(ListaDicas());
        recylerViewDica.setAdapter(dicaAdapter);

        acaoAdapter = new AcaoAdapter(ListaAcoes());
        recyclerViewAcao.setAdapter(acaoAdapter);

        rendaFixaAdapter = new RendaFixaAdapter(ListaRendaFixa());
        recyclerViewRendaFixa.setAdapter(rendaFixaAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerViewAcao.setVisibility(View.VISIBLE);
                    recyclerViewRendaFixa.setVisibility(View.GONE);
                } else if (tab.getPosition() == 1) {
                    // Aba de Renda Fixa
                    recyclerViewAcao.setVisibility(View.GONE);
                    recyclerViewRendaFixa.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


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

        txtCliqueAqui.setOnClickListener((View view ) -> {
            Intent intent = new Intent(InvestimentosActivity.this, SimulacaoActivity.class);
            startActivity(intent);
            finish();

        });
    }

    private List<DicaInvestimento> ListaDicas() {
        List<DicaInvestimento> listaDicas;
        listaDicas = new ArrayList<>();
        listaDicas.add(new DicaInvestimento("Diversificação de Carteira", "Distribua seus investimentos...", DiversificacaoCarteira.class));
        listaDicas.add(new DicaInvestimento("Investimentos de Longo Prazo", "O poder dos juros compostos...",  InvestimentosLongoPrazo.class));
        listaDicas.add(new DicaInvestimento("Renda Fixa vs. Variável", "Entenda as diferenças...",  RendaFixaVariavel.class));

        return listaDicas;
    }
    private List<Acao> ListaAcoes() {
        List<Acao> listaAcoes;
        listaAcoes = new ArrayList<>();
        listaAcoes.add(new Acao("PETR4", "Petrobras", "R$ 36,75", "+2,4%"));
        listaAcoes.add(new Acao("VALE3", "Vale", "68,42", "-1,2%"));
        listaAcoes.add(new Acao("ITUB4", "Itaú Unibanco", "32,18", "+0,8%"));
        return listaAcoes;
    }

    private List<RendaFixa> ListaRendaFixa() {
        List<RendaFixa> RendaFixaList;
        RendaFixaList = new ArrayList<>();
        RendaFixaList.add(new RendaFixa("Tesouro Selic", "2026", 12.75, "a.a.", "Liquidez diária"));
        RendaFixaList.add(new RendaFixa("CDB Banco XYZ", "2 anos", 13.5, "a.a.", "110% do CDI"));

        return RendaFixaList;
    }
}