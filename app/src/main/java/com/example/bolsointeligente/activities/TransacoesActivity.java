package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class TransacoesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListaTransacoesAdapter adapter;
    TextView total_transacoes;
    Database db;
    private int usuarioId;
    private TransacaoDao transacaoDao;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacoes);
        db = Room.databaseBuilder(getApplicationContext(), com.example.bolsointeligente.database.Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        transacaoDao = db.transacaoDao();
        recyclerView = findViewById(R.id.rv_transacoes);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        total_transacoes = findViewById(R.id.txt_total_transacoes);
        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        carregarTotalTransacoes();
        adapter = new ListaTransacoesAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        carregarTransacoes();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(TransacoesActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
               else if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(TransacoesActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.home) {
                    Intent intent = new Intent(TransacoesActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.simulador) {
                    Intent intent = new Intent(TransacoesActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

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
    private void carregarTotalTransacoes() {
        Double total = transacaoDao.getTotalTransacoesPorUsuario(usuarioId);

        if (total == null) {
            total = 0.0; // Se não houver transações, evita erro de null
        }
        total_transacoes.setText(String.format("R$ %.2f", total));
    }

}