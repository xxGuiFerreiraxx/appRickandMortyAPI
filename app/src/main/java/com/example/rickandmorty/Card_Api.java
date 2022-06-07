package com.example.rickandmorty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Card_Api extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nm_personagem;
    private TextView id_personagem;
    private TextView name_personagem;
    private TextView status_personagem;
    private TextView species_personagem;
    private TextView gender_personagem;
    private TextView location_personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        nm_personagem = findViewById(R.id.nm_personagem);
        id_personagem = findViewById(R.id.id);
        name_personagem = findViewById(R.id.name);
        status_personagem = findViewById(R.id.status);
        species_personagem = findViewById(R.id.specie);
        gender_personagem = findViewById(R.id.gender);
        location_personagem = findViewById(R.id.location);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void buscaPersonagens(View view) {
        // Recupera a string de busca.
        String queryString = nm_personagem.getText().toString();
        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //Verificar a disponibilidade  da rede//

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            name_personagem.setText(" ");
            id_personagem.setText("Carregando");
        }
        else {
            if (queryString.length() == 0) {
                name_personagem.setText(" ");
                status_personagem.setText("");
                species_personagem.setText("");
                gender_personagem.setText("");
                location_personagem.setText("");
                id_personagem.setText("Informe um termo de busca");
            } else {
                name_personagem.setText(" ");
                id_personagem.setText("Verifique sua conexão");
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
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

            // inicializa o contador
            int i = 0;
            String id = null;
            String name = null;
            String status = null;
            String species = null;
            String gender = null;
            String name_location = null;
            // Procura pro resultados nos itens do array
            while (i < itemsArray.length() &&
                    (name == null && id == null && status == null && species == null && gender == null && name_location == null)) {
                // Obtem a informação
                JSONObject character= itemsArray.getJSONObject(i);
                JSONObject location= character.getJSONObject("location");
                //  Obter autor e titulo para o item,
                // erro se o campo estiver vazio
                try {
                    id = character.getString("id");
                    name = character.getString("name");
                    status = character.getString("status");
                    species = character.getString("species");
                    gender = character.getString("gender");
                    name_location = location.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // move para a proxima linha
                i++;
            }
            //mostra o resultado qdo possivel.
            if (id != null && name != null) {
                id_personagem.setText(id);
                name_personagem.setText(name);
                status_personagem.setText(status);
                species_personagem.setText(species);
                gender_personagem.setText(gender);
                location_personagem.setText(name_location);

            } else {
                // If none are found, update the UI to show failed results.
                id_personagem.setText("Sem resultados");
                name_personagem.setText(" ");
            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            id_personagem.setText("id");
            name_personagem.setText("name");
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}