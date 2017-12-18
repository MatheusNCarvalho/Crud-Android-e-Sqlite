package com.example.matheus.sqllite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.sqllite.configuracao.ContatoContract;
import com.example.matheus.sqllite.configuracao.SqlLiteConfigHelp;
import com.example.matheus.sqllite.model.Contato;

import java.util.ArrayList;

/**
 * Created by Matheus on 09/11/2017.
 */

public class ContatoDAO {
    private SqlLiteConfigHelp sqlLiteConfig;

    public ContatoDAO(Context context) {
        this.sqlLiteConfig = new SqlLiteConfigHelp(context);
    }

    public  boolean save(Contato contato){
        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try{
            db.beginTransaction();
            ContentValues valores = new ContentValues();

            valores.put(ContatoContract.COLUNA_NOME, contato.getNome());
            valores.put(ContatoContract.COLUNA_INFO, contato.getInfo());
            valores.put(ContatoContract.COLUNA_TELEFONE, contato.getTelefone());

             linhas = db.insert(ContatoContract.NOME_TABELA, null, valores);
            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("Error",e.toString());
            db.endTransaction();
        }


        return  linhas == -1 ? false : true;
    }
    
    
    public  boolean delete(Integer id){
        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();

        long linhas =0;

        try{
            String condicao = ContatoContract.COLUNA_ID+"=?";
            String[] argumentos = {id.toString()};

            db.delete(ContatoContract.NOME_TABELA, condicao, argumentos);
            linhas = db.delete(ContatoContract.NOME_TABELA, condicao, argumentos);
            db.setTransactionSuccessful();
        }catch (Exception e){

            db.endTransaction();
        }
        return linhas <=0 ? false : true;
        
    }

    public boolean update( Contato contato){
        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try{
            db.beginTransaction();
            ContentValues valores = new ContentValues();
            valores.put(ContatoContract.COLUNA_NOME, contato.getNome());
            valores.put(ContatoContract.COLUNA_INFO, contato.getInfo());
            valores.put(ContatoContract.COLUNA_TELEFONE, contato.getTelefone());

            String condicao = ContatoContract.COLUNA_ID+"=?";
            String[] argumentos = {contato.getId().toString()};

            linhas = db.update(ContatoContract.NOME_TABELA, valores, condicao,argumentos);
            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("Error",e.toString());
            db.endTransaction();
        }

        return  linhas != -1 ? false : true;
    }

    public ArrayList<Contato> buscarTodos(){
        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {ContatoContract.COLUNA_ID, ContatoContract.COLUNA_NOME, ContatoContract.COLUNA_INFO, ContatoContract.COLUNA_TELEFONE};

        Cursor cursor = db.query(false,ContatoContract.NOME_TABELA,colunas,null,null,null,null,ContatoContract.COLUNA_NOME+" ASC",null);

        ArrayList<Contato> contatos = new ArrayList<Contato>();
        while(cursor.moveToNext()){
            Contato contato =  new Contato();

            contato.setId(cursor.getInt(cursor.getColumnIndex(ContatoContract.COLUNA_ID)));
            contato.setInfo(cursor.getString(cursor.getColumnIndex(ContatoContract.COLUNA_INFO)));
            contato.setNome(cursor.getString(cursor.getColumnIndex(ContatoContract.COLUNA_NOME)));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex(ContatoContract.COLUNA_TELEFONE)));
            contatos.add(contato);
        }

        cursor.close();
        return contatos;

    }
}
