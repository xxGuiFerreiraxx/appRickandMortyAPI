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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Card_Api extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nm_personagem;
    private TextView id_personagem;
    private TextView name_personagem;
    private TextView status_personagem;
    private String queryString;
    private ListView listView;
    private Button busca;
    private List<Personagem> personagemList;

    Personagem personagem = new Personagem();
    BancoDeDados db=new BancoDeDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        
        nm_personagem = findViewById(R.id.nm_personagem);
        name_personagem = findViewById(R.id.name);
        status_personagem = findViewById(R.id.status);
        listView = findViewById(R.id.listView);
        busca = findViewById(R.id.btnok);

        personagemList = new ArrayList<Personagem>();

        //Verificar a disponibilidade  da rede//

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }

    }

    public void buscaPersonagens(View view) {
        // Recupera a string de busca.
        queryString = "/?name=" + nm_personagem.getText().toString();

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

        }
        else {
            if (queryString.length() == 0) {
                name_personagem.setText(" ");
                status_personagem.setText("");

                id_personagem.setText("@string/erro_notext");
            } else {
                name_personagem.setText(" ");
                id_personagem.setText("@string/erro_nointernet");
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

        if (queryString == null) {
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
                    personagem.setId(object.getString("id"));

                    personagemList.add(personagem);
                }

                ListViewAdapter adapter = new ListViewAdapter(this, R.layout.itenlist, personagemList);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else {
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

                // Procura pro resultados nos itens do array
                while (i < itemsArray.length() &&
                        (name == null && id == null && status == null )) {
                    // Obtem a informação
                    JSONObject character = itemsArray.getJSONObject(i);
                    //  Obter autor e titulo para o item,
                    // erro se o campo estiver vazio
                    try {
                        id = character.getString("id");
                        name = character.getString("name");
                        status = character.getString("status");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // move para a proxima linha
                    i++;
                }
                //mostra o resultado qdo possivel.

                    personagemList.clear();
                    for (int a = 0; a < itemsArray.length(); a++) {
                        JSONObject object = itemsArray.getJSONObject(a);
                        Personagem personagem = new Personagem();
                        personagem.setName(object.getString("name"));
                        personagem.setStatus(object.getString("status"));
                        personagem.setId(object.getString("id"));

                        personagemList.add(personagem);

                        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.itenlist, personagemList);
                        listView.setAdapter(adapter);
                    }





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        }

            @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}