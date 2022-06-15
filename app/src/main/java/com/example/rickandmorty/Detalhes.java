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

import org.json.JSONException;
import org.json.JSONObject;

public class Detalhes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    String queryString;
    TextView nome_do_personagem, status_do_personagem, especie_personagem, genero_personagem, localizacao_personagem;
    Personagem personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent sla = getIntent();
        String id = sla.getStringExtra("id");
        personagem = new Personagem();

        nome_do_personagem = findViewById(R.id.txt_nmpersonagem);
        status_do_personagem = findViewById(R.id.status_personagem);
        especie_personagem = findViewById(R.id.especie_personagem);
        genero_personagem = findViewById(R.id.genero_personagem);
        localizacao_personagem= findViewById(R.id.localizacao_personagem);

        queryString = "/" + (id);

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
            JSONObject object = new JSONObject(data);
            JSONObject object1 = object.getJSONObject("location");
            // Obtem o JSONArray dos personagens

            nome_do_personagem.setText(object.getString("name"));
            status_do_personagem.setText(object.getString("status"));
            especie_personagem .setText(object.getString("species"));
            genero_personagem.setText(object.getString("gender"));
            localizacao_personagem.setText(object1.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
