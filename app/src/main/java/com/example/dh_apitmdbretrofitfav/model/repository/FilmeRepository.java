package com.example.dh_apitmdbretrofitfav.model.repository;

import com.example.dh_apitmdbretrofitfav.model.pojos.Filme;

import io.reactivex.Observable;

import static com.example.dh_apitmdbretrofitfav.model.data.remote.RetrofitService.getApiService;

public class FilmeRepository {
    public Observable<Filme> getFilmes(String apiKey) {
        return getApiService().getAllFilmes(apiKey);
    }
}
