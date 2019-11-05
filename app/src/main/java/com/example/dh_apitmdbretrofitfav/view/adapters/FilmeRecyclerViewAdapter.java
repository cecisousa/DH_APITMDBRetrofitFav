package com.example.dh_apitmdbretrofitfav.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_apitmdbretrofitfav.view.interfaces.OnClick;
import com.example.dh_dh_apitmdbretrofitfav.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmeRecyclerViewAdapter extends RecyclerView.Adapter<FilmeRecyclerViewAdapter.ViewHolder> {

    private List<Result> listaResults;
    private OnClick listener;

    public FilmeRecyclerViewAdapter (List<Result> listaResults, OnClick listener) {
        this.listaResults = listaResults;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result result = listaResults.get(position);
        holder.onBind(result);
        holder.itemView.setOnClickListener(v -> listener.click(result));
    }

    @Override
    public int getItemCount() {
        return listaResults.size();
    }

    public void atualizaLista(List<Result> novaLista) {
        this.listaResults.clear();
        this.listaResults = novaLista;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFilme;
        private TextView txtFilme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFilme = itemView.findViewById(R.id.imgFilme);
            txtFilme = itemView.findViewById(R.id.txtTitulo);
        }

        public void onBind (Result result) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + result.getPosterPath()).into(imgFilme);
            txtFilme.setText(result.getTitle());
        }
    }
}
