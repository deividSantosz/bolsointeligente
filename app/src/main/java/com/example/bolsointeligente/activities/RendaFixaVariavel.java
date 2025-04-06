package com.example.bolsointeligente.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bolsointeligente.R;

public class RendaFixaVariavel extends AppCompatActivity {
    ImageView seta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renda_fixa_variavel);
        seta = findViewById(R.id.seta_longo_prazo);

        seta.setOnClickListener((View view) -> {
            finish();
        });

    }
}