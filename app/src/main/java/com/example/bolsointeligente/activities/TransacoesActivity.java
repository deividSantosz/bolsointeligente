package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Transacao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class TransacoesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacoes);

        recyclerView = findViewById(R.id.rv_transacoes);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

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


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(null);

        ListaTransacoesAdapter adapter = new ListaTransacoesAdapter(listaTransacoes, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(TransacoesActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.Transacoes) {
                    Intent intent = new Intent(TransacoesActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });

    }
}