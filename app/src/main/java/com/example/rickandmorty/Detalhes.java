package com.example.rickandmorty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Detalhes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    String queryString;
    TextView nome_do_personagem, status_do_personagem;
    Personagem personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent intent = getIntent();
        int id = intent.getIntExtra("idPersonagem", 0);
        personagem = new Personagem();

        nome_do_personagem = findViewById(R.id.nome_personagem);
        status_do_personagem = findViewById(R.id.status_personagem);

        queryString = "/" + String.valueOf(id);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected())
        {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new CarregaPersonagem(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);
            // Obtem o JSONArray dos personagens
            JSONArray itemsArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject object = itemsArray.getJSONObject(i);
                Personagem personagem = new Personagem();
                personagem.setName(object.getString("name"));
                personagem.setStatus(object.getString("status"));


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
