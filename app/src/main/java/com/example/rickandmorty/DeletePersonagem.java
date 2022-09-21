package com.example.rickandmorty;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class DeletePersonagem extends AsyncTaskLoader<String> {
    private String mCharacter;
    DeletePersonagem(Context context, String character) {
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
