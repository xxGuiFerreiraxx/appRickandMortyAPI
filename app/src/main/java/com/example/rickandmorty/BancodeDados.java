package com.example.rickandmorty;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class BancoDeDados extends SQLiteOpenHelper {
    public static final String CHARACTER_TABLE_NAME = "tbl_character";

    public static final String COLUMN_ID_CHARACTER = "idCharacter";
    public static final String COLUMN_NAME_CHARACTER = "nameCharacter";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_SPACES = "spaces";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_ORIGIN = "origin";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_EPISODE_CHARACTER = "episode";

    private static final String DATABASE_Nome = "RickandMortyApi.db";
    private static final int DATABASE_VERSION = 1;

    public BancoDeDados(Context context) {
        super(context, DATABASE_Nome, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CriaPersonagem = "create table " + CHARACTER_TABLE_NAME + "( "
                + COLUMN_ID_CHARACTER + " text primary key, "
                + COLUMN_NAME_CHARACTER + " text not null, "
                + COLUMN_STATUS + " text not null, "
                + COLUMN_SPACES + " text, "
                + COLUMN_GENDER + " text, "
                + COLUMN_ORIGIN + " text,"
                + COLUMN_LOCATION + " text)";
        db.execSQL(CriaPersonagem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String addPersonagem(Personagem personagem){
        long resultado;
        //estancia para escrita no banco
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_ID_CHARACTER, personagem.getId());
        values.put(COLUMN_NAME_CHARACTER, personagem.getName());
        values.put(COLUMN_STATUS, personagem.getStatus());
        values.put(COLUMN_SPACES, personagem.getSpaces());
        values.put(COLUMN_GENDER, personagem.getGender());
        values.put(COLUMN_ORIGIN, personagem.getOrigin());
        values.put(COLUMN_LOCATION, personagem.getLocation());

        //inseri no banco
        resultado = db.insert(CHARACTER_TABLE_NAME, null, values);
        db.close();

        if (resultado ==-1) {
            return "Erro ao inserir registro";
        }else{
            return "Registro Inserido com sucesso";
        }
    }
}
