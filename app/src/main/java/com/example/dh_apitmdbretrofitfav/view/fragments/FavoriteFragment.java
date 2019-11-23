package com.example.dh_apitmdbretrofitfav.view.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_apitmdbretrofitfav.util.AppUtil;
import com.example.dh_apitmdbretrofitfav.view.adapters.FavoriteRecyclerViewAdapter;
import com.example.dh_apitmdbretrofitfav.view.interfaces.RemoveFav;
import com.example.dh_dh_apitmdbretrofitfav.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements RemoveFav {
    private RecyclerView recyclerView;
    private FavoriteRecyclerViewAdapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initViews(view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        carregarFavoritos();

        return view;
    }

    private void carregarFavoritos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getContext()) + "/favorites");
        reference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Result> results = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Result result = child.getValue(Result.class);
                    results.add(result);
                }

                adapter.update(results);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewFavoritos);
        adapter = new FavoriteRecyclerViewAdapter(new ArrayList<>(), this);
    }

    @Override
    public void removeFavoriteClickListener(Result result) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getContext()) + "/favorites");
        reference.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot resultSnapshot : dataSnapshot.getChildren()){
                    Result resultFirebase = resultSnapshot.getValue(Result.class);

                    if (result.getId().equals(resultFirebase.getId())) {
                        resultSnapshot.getRef().removeValue();
                        adapter.removeItem(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
