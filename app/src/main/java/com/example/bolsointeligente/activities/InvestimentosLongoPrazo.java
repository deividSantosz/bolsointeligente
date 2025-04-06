package com.example.bolsointeligente.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bolsointeligente.R;

public class InvestimentosLongoPrazo extends AppCompatActivity {
    ImageView seta;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos_longo_prazo);
        seta = findViewById(R.id.seta_longo_prazo);
        seta.setOnClickListener((View view) -> {
            finish();
        });

    }
}