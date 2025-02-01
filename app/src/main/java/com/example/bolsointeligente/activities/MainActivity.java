package com.example.bolsointeligente.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bolsointeligente.R;

public class MainActivity extends AppCompatActivity {

    Button btnEntrar,btnCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         btnEntrar = findViewById(R.id.btnEntrar);
         btnCadastrar = findViewById(R.id.btnCadastrar);


         btnCadastrar.setOnClickListener((View view) -> {
             Intent intent = new Intent(this, CadastroActivity.class);
             startActivity(intent);
        });
         btnEntrar.setOnClickListener((View view) -> {
           Intent intent = new Intent(this, MenuActivity.class);
           startActivity(intent);
         });

    }
}