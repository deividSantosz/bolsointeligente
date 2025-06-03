package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bolsointeligente.database.Database;
import androidx.room.Room;

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
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.fragments.AdicionarTransacao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    Button btnAddTransacao;
    RecyclerView rv_lista_transacoes;
    TextView txt_ver_todos, txtNome, txt_renda, txt_despesas, txt_saldo;
    ListaTransacoesAdapter adapter;
    Database db;
    private int usuarioId;
    private TransacaoDao transacaoDao;

     BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAddTransacao = findViewById(R.id.btn_add_transacao);
        rv_lista_transacoes = findViewById(R.id.rv_transacoes_menu);
        txt_ver_todos = findViewById(R.id.txt_ver_todos);
        txtNome = findViewById(R.id.textNomeMenu);
        txt_renda = findViewById(R.id.txt_renda);
        txt_despesas = findViewById(R.id.txt_despesas);
        txt_saldo = findViewById(R.id.txt_saldo);

        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        txtNome.setText(UsuarioSingleton.getInstance().getuserNome());
        transacaoDao = db.transacaoDao();
        adapter = new ListaTransacoesAdapter(new ArrayList<>(), this);
        rv_lista_transacoes.setLayoutManager(new LinearLayoutManager(this));
        rv_lista_transacoes.setAdapter(adapter);

        atualizarDadosFinanceiros();
        carregarTransacoes();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    return true;
                } else if (itemId == R.id.estatisticas) {
                    Intent intent = new Intent(MenuActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.simulador) {
                    Intent intent = new Intent(MenuActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.perfil) {
                    Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);

        btnAddTransacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdicionarTransacao fragment = new AdicionarTransacao();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null) // Para permitir que o usuário volte para a tela anterior
                        .commit();
            }
        });

       txt_ver_todos.setOnClickListener((View view) -> {
           Intent intent = new Intent(MenuActivity.this, TransacoesActivity.class);
           startActivity(intent);
       });
}

private void carregarTransacoes() {
        List<Transacao> transacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);
        if (transacoes != null && !transacoes.isEmpty()) {
            adapter.listaTransacoes.clear();
            adapter.listaTransacoes.addAll(transacoes);
            adapter.notifyDataSetChanged();
        }
    }
    private void atualizarDadosFinanceiros() {
        List<Transacao> transacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);
        Double renda = db.usuarioDao().getUserRenda(usuarioId);
        if (renda == null) {
            renda = 0.0;
        }

        Double despesas = 0.0;
        Double entradas = 0.0;
        for (Transacao transacao : transacoes) {
            double valor = transacao.getValor();
            if (valor < 0) {
                despesas += Math.abs(valor);
            } else if (valor > 0) {
                entradas += valor;
            }
        }
        Double saldo = renda + entradas - despesas;
        txt_renda.setText(String.format("%.2f", renda));
        txt_despesas.setText(String.format("%.2f", despesas));
        txt_saldo.setText(String.format("%.2f", saldo));
    }



}