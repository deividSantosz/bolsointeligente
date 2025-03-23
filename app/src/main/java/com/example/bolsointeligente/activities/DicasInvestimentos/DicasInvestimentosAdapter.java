package com.example.bolsointeligente.activities.DicasInvestimentos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


import com.example.bolsointeligente.R;
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
        Context context = holder.itemView.getContext();

        holder.saibaMais.setOnClickListener(v -> {
            Intent intent = new Intent(context, dica.getActivityDestino()); // Aqui pegamos a Activity correspondente
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaDicas.size();
    }

}
