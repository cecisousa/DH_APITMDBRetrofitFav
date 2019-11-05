package com.example.dh_apitmdbretrofitfav.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_apitmdbretrofitfav.model.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Result>> listaFilme = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Result>> getListaFilme() {
        return this.listaFilme;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getAllFilmes(String apiKey) {
        disposable.add(
                repository.getFilmes(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> loading.setValue(true))
                .doOnTerminate(() -> loading.setValue(false))
                .subscribe(filmeResult -> {
                    listaFilme.setValue(filmeResult.getResults());
                }, throwable -> {
                    Log.i("LOG", "Erro" + throwable.getMessage());
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
