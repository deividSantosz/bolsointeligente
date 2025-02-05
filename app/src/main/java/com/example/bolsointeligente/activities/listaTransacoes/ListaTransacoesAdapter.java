package com.example.bolsointeligente.activities.listaTransacoes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.database.Transacao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListaTransacoesAdapter extends RecyclerView.Adapter<ViewHolderListaTransacoes> {

    private List<Transacao> listaTransacoes;
    private Context activityContext;

    public ListaTransacoesAdapter(List<Transacao> listaTransacoes, Context activityContext) {
        this.listaTransacoes = listaTransacoes;
        this.activityContext = activityContext;
    }
    @NonNull
    @Override
    public ViewHolderListaTransacoes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderListaTransacoes viewHolder = new ViewHolderListaTransacoes(inflater.inflate(R.layout.recycler_view_transacao, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListaTransacoes holder, int position) {

        Transacao transacao = listaTransacoes.get(position);

        Log.d("ListaTransacoes", "Posição: " + position + " Tamanho da lista: " + listaTransacoes.size());
        Date dataTransacao = new Date(transacao.getData());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataFormatada = formatter.format(dataTransacao);
        holder.txt_data.setText(dataFormatada);

        holder.txt_categoria.setText(transacao.getCategoria());

        if (transacao.getValor()<0) {
            holder.txt_valor.setText(String.format(Locale.getDefault(), "R$ %.2f", transacao.getValor()));
            holder.txt_valor.setTextColor(activityContext.getResources().getColor(R.color.vermelho));
            holder.txt_descricao.setText("Saída");
        }
        else {
            holder.txt_valor.setText(String.format(Locale.getDefault(), "R$ %.2f", transacao.getValor()));
        }
    }

    @Override
    public int getItemCount() {
        return listaTransacoes.size();
    }
}
