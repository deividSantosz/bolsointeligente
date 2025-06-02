package com.example.bolsointeligente.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bolsointeligente.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FundosImobiliariosActivity extends AppCompatActivity {
    ImageView seta;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundos_imobiliarios);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        seta = findViewById(R.id.seta_fundos);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.simulador) {
                    return true;
                }
                else if (itemId == R.id.home) {
                    Intent intent = new Intent(FundosImobiliariosActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(FundosImobiliariosActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }

                else if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(FundosImobiliariosActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
        bottomNavigationView.setSelectedItemId(R.id.simulador);


        seta.setOnClickListener((View view) -> {
            finish();
        });

    }
}

