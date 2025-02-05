package com.example.bolsointeligente.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Transacao;

import java.util.ArrayList;
import java.util.List;

public class TransacoesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacoes);

        recyclerView = findViewById(R.id.rv_transacoes);

        List<Transacao> listaTransacoes = new ArrayList<>();
        listaTransacoes.add(new Transacao(1, - 150.0, "Pagamento de salário", "Alimentação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(2, -50.0, "Compra supermercado", "Educação", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(3, 200.0, "Venda de item", "Outros", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(4, -30.0, "Almoço", "Investimentos", System.currentTimeMillis(), 1));
        listaTransacoes.add(new Transacao(5, -100.0, "Pagamento de contas", "Alimentação", System.currentTimeMillis(), 1));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(null);

        ListaTransacoesAdapter adapter = new ListaTransacoesAdapter(listaTransacoes, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}