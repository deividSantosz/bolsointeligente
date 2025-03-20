package com.example.bolsointeligente.activities.dicas_investimentos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ViewHolderListaTransacoes;
import com.example.bolsointeligente.database.DicaInvestimento;

public class DicasInvestimentosAdapter extends RecyclerView.Adapter<ViewHolderItemDica>{

    private List<DicaInvestimento> listaDicas;

    public DicasInvestimentosAdapter(List<DicaInvestimento> listaDicas) {
        this.listaDicas = listaDicas;
    }
    @NonNull
    @Override
    public ViewHolderItemDica onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderItemDica viewHolder = new ViewHolderItemDica(inflater.inflate(R.layout.recycler_view_item_dica, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItemDica holder, int position) {
        DicaInvestimento dica = listaDicas.get(position);
        holder.tituloDica.setText(dica.getTitulo());
        holder.descricaoDica.setText(dica.getDescricao());
    }

    @Override
    public int getItemCount() {
        return listaDicas.size();
    }

}
