package com.example.dh_apitmdbretrofitfav.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_apitmdbretrofitfav.view.interfaces.RemoveFav;
import com.example.dh_dh_apitmdbretrofitfav.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder> {
    private List<Result> results;
    private RemoveFav listener;

    public FavoriteRecyclerViewAdapter(List<Result> results, RemoveFav listener){
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new FavoriteRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.onBind(result);
        holder.imageFavorite.setOnClickListener(v -> listener.removeFavoriteClickListener(result));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void update(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void removeItem(Result result){
        results.remove(result);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFilme;
        private TextView txtTituloFilme;
        private ImageView imageFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFilme = itemView.findViewById(R.id.imgFilme);
            txtTituloFilme = itemView.findViewById(R.id.txtTitulo);
            imageFavorite = itemView.findViewById(R.id.imageFavorite);
        }

        public void onBind(Result result) {
            txtTituloFilme.setText(result.getTitle());
            Picasso.get().load("http://image.tmdb.org/t/p/w500/" + result.getPosterPath()).into(imgFilme);

        }
    }
}
