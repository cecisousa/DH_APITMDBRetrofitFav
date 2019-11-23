package com.example.dh_apitmdbretrofitfav.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dh_apitmdbretrofitfav.model.pojos.Result;
import com.example.dh_apitmdbretrofitfav.model.repository.FilmeRepository;
import com.example.dh_apitmdbretrofitfav.util.AppUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Result>> listaFilme = new MutableLiveData<>();
    public MutableLiveData<Result> favoriteAdded = new MutableLiveData<>();
    private MutableLiveData<Throwable> resultLiveDataError = new MutableLiveData<>();
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

    public void salvarFavorito(Result result) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getApplication()) + "/favorites");
        String key = reference.push().getKey();
        reference.child(key).setValue(result);

        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Result movie = dataSnapshot.getValue(Result.class);
                favoriteAdded.setValue(movie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                resultLiveDataError.setValue(databaseError.toException());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
