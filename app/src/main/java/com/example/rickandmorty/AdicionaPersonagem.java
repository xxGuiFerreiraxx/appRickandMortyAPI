package com.example.rickandmorty;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class Insert extends AsyncTaskLoader<String> {
    private String mCharacter;
    Insert(Context context, String character) {
        super(context);
        mCharacter = character;

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {

        return Conexao.adicionaPersonagem(mCharacter);
    }
}