package com.example.dh_apitmdbretrofitfav.view.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_dh_apitmdbretrofitfav.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalheFragment extends Fragment {


    public DetalheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhe, container, false);

        ImageView imageView = view.findViewById(R.id.imgDetalhe);

        if (getArguments() != null) {
            Result result = getArguments().getParcelable("Result");

            Toast.makeText(getContext(), "Filme: " + result.getTitle(), Toast.LENGTH_LONG).show();

            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + result.getPosterPath()).into(imageView);

        }

        return view;


    }

}
