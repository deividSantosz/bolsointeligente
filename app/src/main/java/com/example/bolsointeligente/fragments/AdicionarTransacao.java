package com.example.bolsointeligente.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.CadastroActivity;
import com.example.bolsointeligente.activities.MenuActivity;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdicionarTransacao extends Fragment {

    private AutoCompleteTextView autoCompleteCategory;
    private EditText editDescricao, editValor, editDate;
    private Button btnAddTransaction;
    Database db;
    private TabLayout tabLayout;
    private boolean isSaida = false;
    private TransacaoDao transacaoDao;
    private int usuarioId = (int) UsuarioSingleton.getInstance().getUserId();

    private final Calendar calendario = Calendar.getInstance();

    public AdicionarTransacao() {
        // Required empty public constructor
    }

    public static AdicionarTransacao newInstance(String param1, String param2) {
        AdicionarTransacao fragment = new AdicionarTransacao();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar_transacao, container, false);
        db = Room.databaseBuilder(requireContext(), Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // Inicializar os campos
        tabLayout = view.findViewById(R.id.tab_income_expense);
        autoCompleteCategory = view.findViewById(R.id.autoCompleteCategory);
        editDescricao = view.findViewById(R.id.edit_descricao);
        editValor = view.findViewById(R.id.edit_valor);
        editDate = view.findViewById(R.id.edit_date);
        btnAddTransaction = view.findViewById(R.id.btn_add_transaction);

        // Inicializar o DAO do Room Database
        transacaoDao = db.transacaoDao();


        // Lista de categorias para o AutoCompleteTextView
        String[] categorias = {"Alimentação", "Transporte", "Lazer", "Saúde", "Outros", "Animal", "Beleza", "Educação", "Investimentos"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categorias);
        autoCompleteCategory.setAdapter(adapter);
        autoCompleteCategory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                autoCompleteCategory.showDropDown();
            }
        });

        // Configurar campo de data para abrir um DatePickerDialog
        editDate.setOnClickListener(v -> mostrarDatePicker());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isSaida = tab.getPosition() == 1; // Índice 1 representa "Saída"
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        // Configurar botão para salvar a transação
        btnAddTransaction.setOnClickListener(view2 -> {
            salvarTransacao(); // Salva a transação antes de trocar de tela
            Intent intent = new Intent(requireContext(), MenuActivity.class);
            startActivity(intent);
        });
        return view;
    }
    private void mostrarDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    calendario.set(Calendar.YEAR, year);
                    calendario.set(Calendar.MONTH, month);
                    calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    editDate.setText(formato.format(calendario.getTime()));
                },
                calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }
    private void salvarTransacao() {
        String descricao = editDescricao.getText().toString();
        String categoria = autoCompleteCategory.getText().toString();
        String valorTexto = editValor.getText().toString();
        String dataTexto = editDate.getText().toString();

        if (categoria.isEmpty() || valorTexto.isEmpty() || dataTexto.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorTexto);
        if (isSaida) {
            valor = -valor; // Se for uma saída, transforma o valor em negativo
        }
        long data = calendario.getTimeInMillis(); // Converte a data para timestamp

        Transacao transacao = new Transacao();
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setCategoria(categoria);
        transacao.setData(data);
        transacao.setIdUsuario(usuarioId); // Relaciona ao usuário

        // Salvar no banco
        transacaoDao.inserirTransacao(transacao);
        Toast.makeText(getContext(), "Transação adicionada com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
