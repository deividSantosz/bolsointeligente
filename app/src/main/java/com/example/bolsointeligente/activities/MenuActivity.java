package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.fragments.AdicionarTransacao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    Button btnAddTransacao;
    RecyclerView rv_lista_transacoes;
    TextView txt_ver_todos;
    public BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAddTransacao = findViewById(R.id.btn_add_transacao);
        rv_lista_transacoes = findViewById(R.id.rv_transacoes_menu);
        txt_ver_todos = findViewById(R.id.txt_ver_todos);
        // Dados fictícios
        List<Transacao> listaTransacoes = new ArrayList<>();
        listaTransacoes.add(new Transacao(1, - 150.0, "Pagamento de salário", "Alimentação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(2, -50.0, "Compra supermercado", "Educação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(3, 200.0, "Venda de item", "Outros", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(4, -30.0, "Almoço", "Investimentos", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -100.0, "Pagamento de contas", "Saúde", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -80.0, "Pagamento de contas", "Transporte", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -100.0, "Pagamento de contas", "Beleza", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -500, "Pagamento de contas", "Lazer", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -67, "Pagamento de contas", "Animal", System.currentTimeMillis(), 1));
        int maxItens = 4; // Limite de itens a serem exibidos
        List<Transacao> listaLimitada = listaTransacoes.subList(0, Math.min(listaTransacoes.size(), maxItens));



        rv_lista_transacoes.setLayoutManager(new LinearLayoutManager(this));
        ListaTransacoesAdapter adapter = new ListaTransacoesAdapter(listaLimitada, this);
        rv_lista_transacoes.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.Transacoes) {
                    Intent intent = new Intent(MenuActivity.this, TransacoesActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(MenuActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
       btnAddTransacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criando uma instância do fragmento
                AdicionarTransacao fragment = new AdicionarTransacao();

                // Iniciando a transação para substituir o conteúdo do FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment) // Aqui estamos substituindo o fragmento atual pelo novo
                        .addToBackStack(null) // Para permitir que o usuário volte para a tela anterior
                        .commit();
            }
        });

       txt_ver_todos.setOnClickListener((View view) -> {
           Intent intent = new Intent(MenuActivity.this, TransacoesActivity.class);
           startActivity(intent);
       });
}
}