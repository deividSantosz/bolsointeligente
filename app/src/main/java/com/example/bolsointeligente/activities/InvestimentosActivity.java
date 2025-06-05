package com.example.bolsointeligente.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bolsointeligente.config.Config;
import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.DicasInvestimentos.DicasInvestimentosAdapter;
import com.example.bolsointeligente.activities.Acoes.AcaoAdapter;
import com.example.bolsointeligente.activities.RendaFixa.RendaFixaAdapter;
import com.example.bolsointeligente.api.CotacaoResponse;
import com.example.bolsointeligente.database.Acao;
import com.example.bolsointeligente.database.DicaInvestimento;
import com.example.bolsointeligente.database.RendaFixa;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.example.bolsointeligente.api.AcaoApiService;
import com.example.bolsointeligente.api.RetrofitClient;
import com.example.bolsointeligente.database.CotacaoAcaoResponse;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestimentosActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private TextView  txtCliqueAqui;
    private RecyclerView recylerViewDica, recyclerViewAcao, recyclerViewRendaFixa, recyclerViewTiposInvestimento;
    private DicasInvestimentosAdapter dicaAdapter;
    private DicasInvestimentosAdapter tiposInvestimentosAdapter;
    private AcaoAdapter acaoAdapter;
    private RendaFixaAdapter rendaFixaAdapter;
    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        txtCliqueAqui = findViewById(R.id.txt_clique_aqui_investimentos);
        tabLayout = findViewById(R.id.tabLayout);
        recyclerViewTiposInvestimento = findViewById(R.id.rv_tipo_investimento);
        recyclerViewAcao = findViewById(R.id.recycleviewAcao);
        recylerViewDica = findViewById(R.id.recyclerDicas);
        recyclerViewRendaFixa = findViewById(R.id.recyclerViewRendaFixa);
        recylerViewDica.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAcao.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTiposInvestimento.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRendaFixa.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRendaFixa.setVisibility(View.GONE);


        dicaAdapter = new DicasInvestimentosAdapter(ListaDicas());
        recylerViewDica.setAdapter(dicaAdapter);

        tiposInvestimentosAdapter = new DicasInvestimentosAdapter(ListaTiposInvestimento());
        recyclerViewTiposInvestimento.setAdapter(tiposInvestimentosAdapter);


        List<Acao> listaAcoes = new ArrayList<>();
        acaoAdapter = new AcaoAdapter(listaAcoes);
        recyclerViewAcao.setAdapter(acaoAdapter);

        buscarCotacao("PETR4", listaAcoes);
        buscarCotacao("VALE3", listaAcoes);
        buscarCotacao("ITUB4", listaAcoes);


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


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.simulador) {
                    return true;
                }
                else if (itemId == R.id.home) {
                    Intent intent = new Intent(InvestimentosActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(InvestimentosActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }

                else if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(InvestimentosActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
         bottomNavigationView.setSelectedItemId(R.id.simulador);


        txtCliqueAqui.setOnClickListener((View view ) -> {
            Intent intent = new Intent(InvestimentosActivity.this, SimulacaoActivity.class);
            startActivity(intent);
            finish();

        });
    }

    private List<DicaInvestimento> ListaDicas() {
        List<DicaInvestimento> listaDicas;
        listaDicas = new ArrayList<>();
        listaDicas.add(new DicaInvestimento("Diversificação de Carteira", "Distribua seus investimentos...", DiversificacaoCarteiraActivity.class));
        listaDicas.add(new DicaInvestimento("Investimentos de Longo Prazo", "O poder dos juros compostos...",  InvestimentosLongoPrazo.class));
        listaDicas.add(new DicaInvestimento("Renda Fixa vs. Variável", "Entenda as diferenças...",  RendaFixaVariavelActivity.class));

        return listaDicas;
    }
    private List<RendaFixa> ListaRendaFixa() {
        List<RendaFixa> RendaFixaList;
        RendaFixaList = new ArrayList<>();
        RendaFixaList.add(new RendaFixa("Tesouro Selic", "2026", 12.75, "a.a.", "Liquidez diária"));
        RendaFixaList.add(new RendaFixa("CDB Banco XYZ", "2 anos", 13.5, "a.a.", "110% do CDI"));

        return RendaFixaList;
    }

    private List<DicaInvestimento> ListaTiposInvestimento() {
        List<DicaInvestimento> listaTiposInvestimentos;
        listaTiposInvestimentos = new ArrayList<>();
        listaTiposInvestimentos.add(new DicaInvestimento("Fundos Imobiliarios", "Aprenda o que são fundos imobiliarios.", FundosImobiliariosActivity.class));
        listaTiposInvestimentos.add(new DicaInvestimento("Ações Brasileiras", "Entenda como funciona as ações no Brasil.", AcoesBrasileirasActivity.class));
        listaTiposInvestimentos.add(new DicaInvestimento("Criptomoedas", "Saiba como investir na moeda do futuro.",  CriptomoedasActivity.class));

        return listaTiposInvestimentos;
    }

    private void buscarCotacao(String symbol, List<Acao> listaAcoes) {
        String token = Config.loadProperties(this);
        AcaoApiService apiService = RetrofitClient.getApiService();
        Call<CotacaoResponse> call = apiService.getCotacao(symbol, token);
        Log.d("Token", "Token via config:" + token);
        call.enqueue(new Callback<CotacaoResponse>() {
            @Override
            public void onResponse(Call<CotacaoResponse> call, Response<CotacaoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Acessando os resultados dentro da resposta
                    CotacaoResponse cotacaoResponse = response.body();
                    List<CotacaoAcaoResponse> cotacoes = cotacaoResponse.getResults();
                    // Garantir que a lista não está vazia
                    if (!cotacoes.isEmpty()) {
                        CotacaoAcaoResponse cotacao = cotacoes.get(0); // Pegando o primeiro item da lista

                        Log.d("API Response", "Cotação recebida: " + cotacao.toString());
                        String preco = String.format("%.2f", cotacao.getRegularMarketPrice());
                        String variacao = String.format("%.2f%%", cotacao.getChangePercent());

                        // Atualizando a lista de ações
                        listaAcoes.add(new Acao(
                                cotacao.getSymbol(),
                                cotacao.getNome(),
                                preco,
                                variacao
                        ));

                        // Notifica o adaptador sobre a atualização
                        acaoAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("API Error", "Código de erro HTTP: " + response.code());
                    Log.e("API Error", "Mensagem de erro: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CotacaoResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}