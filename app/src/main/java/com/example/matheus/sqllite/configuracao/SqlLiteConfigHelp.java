package com.example.matheus.sqllite.configuracao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matheus.sqllite.model.Contato;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Matheus on 09/11/2017.
 */

public class SqlLiteConfigHelp extends SQLiteOpenHelper {
    private static final String SQL_CREATE = " CREATE TABLE IF NOT EXISTS" + ContatoContract.NOME_TABELA + "(" + ContatoContract.COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ContatoContract.COLUNA_INFO + " TEXT, "
            + ContatoContract.COLUNA_NOME + " TEXT, "
            + ContatoContract.COLUNA_TELEFONE + " TEXT );";

    private static final String SQL_DROP = " DROP TABLE IF NOT EXISTS " + ContatoContract.NOME_TABELA;


    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "agenda.db";

    public SqlLiteConfigHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.beginTransaction();
//        db.execSQL(SQL_CREATE);
//        db.setTransactionSuccessful();

        for (int i = 0; i < getArraySQLCreate().size(); i++) {
            String sql = getArraySQLCreate().get(i);
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        db.execSQL(SQL_DROP);
        onCreate(db);
    }


    public ArrayList<String> getArraySQLCreate() {

//        scriptSQLCreate = new ArrayList<String>();
        ArrayList<String> scriptSQLCreate = new ArrayList<String>();

        scriptSQLCreate.add(" CREATE TABLE IF NOT EXISTS " + ContatoContract.NOME_TABELA + "(" + ContatoContract.COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContatoContract.COLUNA_INFO + " TEXT, "
                + ContatoContract.COLUNA_NOME + " TEXT, "
                + ContatoContract.COLUNA_TELEFONE + " TEXT );");

        scriptSQLCreate.add(" CREATE TABLE IF NOT EXISTS " + UsuarioContract.NOME_TABELA + "(" + UsuarioContract.COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UsuarioContract.COLUNA_NOME + " TEXT, "
                + UsuarioContract.COLUNA_CPF + " TEXT, "
                + UsuarioContract.COLUNA_TELEFONE + " TEXT, "
                + UsuarioContract.COLUNA_EMAIL + " TEXT );");

        return scriptSQLCreate;
    }
}
