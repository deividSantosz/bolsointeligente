package com.example.bolsointeligente.fragments;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolsointeligente.R;

public class AdicionarTransacao extends Fragment {

    private AutoCompleteTextView autoCompleteCategory;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AdicionarTransacao() {
        // Required empty public constructor
    }

    public static AdicionarTransacao newInstance(String param1, String param2) {
        AdicionarTransacao fragment = new AdicionarTransacao();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar o layout para o fragmento
        View view = inflater.inflate(R.layout.fragment_adicionar_transacao, container, false);

        // Inicializar o AutoCompleteTextView
        autoCompleteCategory = view.findViewById(R.id.autoCompleteCategory);

        // Lista de categorias para o AutoCompleteTextView
        String[] categorias = {"Alimentação", "Transporte", "Lazer", "Saúde"};

        // Criar um ArrayAdapter para preencher o AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categorias);

        // Configurar o AutoCompleteTextView com o adapter
        autoCompleteCategory.setAdapter(adapter);

        // Forçar a exibição do dropdown ao focar no campo
        autoCompleteCategory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                autoCompleteCategory.showDropDown();
            }
        });

        // Configurar o AutoCompleteTextView para mostrar sugestões após o primeiro caractere
        autoCompleteCategory.setThreshold(1);

        return view;
    }
}
