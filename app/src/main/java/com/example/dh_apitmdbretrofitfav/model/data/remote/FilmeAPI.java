package com.example.dh_apitmdbretrofitfav.model.data.remote;

import com.example.dh_apitmdbretrofitfav.model.pojos.Filme;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmeAPI {

    @GET("movie/popular")
    Observable<Filme> getAllFilmes(@Query("api_key") String apiKey);
}
