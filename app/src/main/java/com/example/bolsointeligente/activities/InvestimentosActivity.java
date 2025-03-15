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

import com.example.bolsointeligente.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InvestimentosActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


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