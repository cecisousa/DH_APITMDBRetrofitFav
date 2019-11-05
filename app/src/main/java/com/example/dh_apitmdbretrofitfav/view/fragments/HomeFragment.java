package com.example.dh_apitmdbretrofitfav.view.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_apitmdbretrofitfav.view.adapters.FilmeRecyclerViewAdapter;
import com.example.dh_apitmdbretrofitfav.view.interfaces.OnClick;
import com.example.dh_apitmdbretrofitfav.viewmodel.HomeFragmentViewModel;
import com.example.dh_dh_apitmdbretrofitfav.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnClick {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private HomeFragmentViewModel viewModel;
    private FilmeRecyclerViewAdapter adapter;
    private List<Result> listaResults = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAllFilmes("bde8033d3274c91b292a5293c6349052");

        viewModel.getListaFilme().observe(this, resultadoLista -> {
            adapter.atualizaLista(resultadoLista);
        });

        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }


    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewFilmes);
        progressBar = view.findViewById(R.id.progressBar);
        viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        adapter = new FilmeRecyclerViewAdapter(listaResults, this);

    }

    @Override
    public void click(Result result) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Result", result);
        Fragment novoFragment = new DetalheFragment();
        novoFragment.setArguments(bundle);
        replaceFragment(novoFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }
}
