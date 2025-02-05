package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
    public BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAddTransacao = findViewById(R.id.btn_add_transacao);
        rv_lista_transacoes = findViewById(R.id.rv_transacoes_menu);
        // Dados fictícios
        List<Transacao> listaTransacoes = new ArrayList<>();
        listaTransacoes.add(new Transacao(1, - 150.0, "Pagamento de salário", "Alimentação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(2, -50.0, "Compra supermercado", "Educação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(3, 200.0, "Venda de item", "Outros", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(4, -30.0, "Almoço", "Investimentos", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -100.0, "Pagamento de contas", "Alimentação", System.currentTimeMillis(), 1));


        rv_lista_transacoes.setLayoutManager(new LinearLayoutManager(this));
        ListaTransacoesAdapter adapter = new ListaTransacoesAdapter(listaTransacoes, this);
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
                return false;
            }

        });
       /* btnAddTransacao.setOnClickListener(new View.OnClickListener() {
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
        }); */
}
}