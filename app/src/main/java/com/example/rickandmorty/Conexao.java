package com.example.rickandmorty;

import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conexao {
    private static final String LOG_TAG = Conexao.class.getSimpleName();
    // Constantes utilizadas pela API
    // URL para a API do Rick and Morty.
    private static final String PERSONAGENS_URL = "https://rickandmortyapi.com/api/character";
    // Parametros da string de Busca
    private static final String QUERY_PARAM = "name";
    // Limitador da qtde de resultados
    private static final String MAX_RESULTS = "maxResults";
    // Parametro do tipo de impressão
    private static final String TIPO_IMPRESSAO = "printType";

    static String buscaInfosPersonagens(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;

        try {
            Uri builtURI;
            if(queryString == null){
                builtURI = Uri.parse(PERSONAGENS_URL).buildUpon()
                        .build();
            }
            else {
                String url1 = PERSONAGENS_URL + queryString;
                 //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon()
                        .build();


            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Usa o StringBuilder para receber a resposta.
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Adiciona a linha a string.
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // se o stream estiver vazio não faz nada
                return null;
            }
            characterJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // escreve o Json no log
        Log.d(LOG_TAG, characterJSONString);
        return characterJSONString;
    }

    static void adicionaPersonagem(Character character){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;



        try {
            Uri builtURI;
            if(character == null){
                builtURI = Uri.parse(PERSONAGENS_URL).buildUpon()
                        .build();
            }
            else {
                String url1 = PERSONAGENS_URL;
                //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon()
                        .build();


            }

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, "UTF-8");

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String url = jsonReader.nextName(); // Fetch the next key
                if (url.equals("URL DA API")) { // Check if desired key
                    Gson gson = new Gson();
                    String characterAdd = gson.toJson(character);
                    // Fetch the value as a String
                    characterAdd = jsonReader.nextString();

                    // Do something with the value
                    // ...

                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void updatePersonagem(Character character){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;



        try {
            Uri builtURI;
            if(character == null){
                builtURI = Uri.parse(PERSONAGENS_URL).buildUpon()
                        .build();
            }
            else {
                String url1 = PERSONAGENS_URL;
                //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon()
                        .build();


            }

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, "UTF-8");

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String url = jsonReader.nextName(); // Fetch the next key
                if (url.equals("URL DA API")) { // Check if desired key
                    Gson gson = new Gson();
                    String characterUpdate = gson.toJson(character);
                    // Fetch the value as a String
                    characterUpdate = jsonReader.nextString();

                    // Do something with the value
                    // ...

                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("SET");
            urlConnection.connect();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void deletePersonagem(String character){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String characterJSONString = null;



        try {
            Uri builtURI;
            if(character == null){
                builtURI = Uri.parse(PERSONAGENS_URL).buildUpon()
                        .build();
            }
            else {
                String url1 = PERSONAGENS_URL;
                //Construção da URI de Busca
                builtURI = Uri.parse(url1).buildUpon()
                        .build();


            }

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, "UTF-8");

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String url = jsonReader.nextName(); // Fetch the next key
                if (url.equals("URL DA API")) { // Check if desired key
                    Gson gson = new Gson();
                    String characterUpdate = gson.toJson(character);
                    // Fetch the value as a String
                    characterUpdate = jsonReader.nextString();

                    // Do something with the value
                    // ...

                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.connect();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


