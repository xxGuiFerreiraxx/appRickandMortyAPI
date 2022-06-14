package com.example.rickandmorty;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CarregaPersonagem extends AsyncTaskLoader<String> {
    private String mQueryString;
    CarregaPersonagem(Context context, String queryString) {
        super(context);
        mQueryString = queryString;

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {

        return Conexao.buscaInfosPersonagens(mQueryString);
    }
}
