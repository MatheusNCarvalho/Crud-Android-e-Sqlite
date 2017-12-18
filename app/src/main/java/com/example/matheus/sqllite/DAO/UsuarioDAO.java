package com.example.matheus.sqllite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.sqllite.configuracao.UsuarioContract;
import com.example.matheus.sqllite.configuracao.SqlLiteConfigHelp;
import com.example.matheus.sqllite.model.Usuario;


import java.util.ArrayList;

/**
 * Created by Matheus on 11/11/2017.
 */

public class UsuarioDAO {

    private SqlLiteConfigHelp sqlLiteConfig;

    public UsuarioDAO(Context context) {
        this.sqlLiteConfig = new SqlLiteConfigHelp(context);
    }


    public  boolean save(Usuario usuario){
        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try{
            db.beginTransactionNonExclusive();
            ContentValues valores = new ContentValues();
            valores.put(UsuarioContract.COLUNA_NOME, usuario.getNome());
            valores.put(UsuarioContract.COLUNA_EMAIL, usuario.getEmail());
            valores.put(UsuarioContract.COLUNA_TELEFONE, usuario.getTelefone());
            valores.put(UsuarioContract.COLUNA_CPF, usuario.getCpf());

            linhas = db.insert(UsuarioContract.NOME_TABELA, null, valores);

            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("Error",e.toString());
            db.endTransaction();
        }finally {
            db.endTransaction();
        }


        return  linhas == -1 ? false : true;
    }


    public  boolean delete(Integer id){
        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();

        long linhas =0;


        try{
            db.beginTransactionNonExclusive();
            String condicao = UsuarioContract.COLUNA_ID+"=?";
            String[] argumentos = {id.toString()};

//            db.delete(UsuarioContract.NOME_TABELA, condicao, argumentos);
            linhas = db.delete(UsuarioContract.NOME_TABELA, condicao, argumentos);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("Error", e.toString());
            db.endTransaction();
        }finally {
            db.endTransaction();
        }
        return linhas <=0 ? false : true;

    }

    public boolean update( Usuario usuario){
        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try{
            db.beginTransactionNonExclusive();
            ContentValues valores = new ContentValues();
            valores.put(UsuarioContract.COLUNA_NOME, usuario.getNome());
            valores.put(UsuarioContract.COLUNA_EMAIL, usuario.getEmail());
            valores.put(UsuarioContract.COLUNA_TELEFONE, usuario.getTelefone());
            valores.put(UsuarioContract.COLUNA_CPF, usuario.getCpf());

            String condicao = UsuarioContract.COLUNA_ID+"=?";
            String[] argumentos = {usuario.getId().toString()};

            linhas = db.update(UsuarioContract.NOME_TABELA, valores, condicao,argumentos);
            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("Error",e.toString());
            db.endTransaction();
        }finally {
            db.endTransaction();
        }

        return  linhas != -1 ? false : true;
    }

    public ArrayList<Usuario> buscarTodos(){
        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {UsuarioContract.COLUNA_ID, UsuarioContract.COLUNA_NOME, UsuarioContract.COLUNA_EMAIL, UsuarioContract.COLUNA_TELEFONE, UsuarioContract.COLUNA_CPF};

        Cursor cursor = db.query(false,UsuarioContract.NOME_TABELA,colunas,null,null,null,null,UsuarioContract.COLUNA_NOME+" ASC",null);

        ArrayList<Usuario> contatos = new ArrayList<Usuario>();
        while(cursor.moveToNext()){
            Usuario usuario =  new Usuario();

            usuario.setId(cursor.getInt(cursor.getColumnIndex(UsuarioContract.COLUNA_ID)));
            usuario.setCpf(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_CPF)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_NOME)));
            usuario.setTelefone(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_TELEFONE)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_EMAIL)));
            contatos.add(usuario);
        }

        cursor.close();
        return contatos;

    }

    public ArrayList<Usuario> buscarPorNome(String nome){
        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {UsuarioContract.COLUNA_ID, UsuarioContract.COLUNA_NOME, UsuarioContract.COLUNA_EMAIL, UsuarioContract.COLUNA_TELEFONE, UsuarioContract.COLUNA_CPF};

//        mDb.query(true, DATABASE_NAMES_TABLE, new String[] { KEY_ROWID,
//                        KEY_NAME }, KEY_NAME + " LIKE ?",
//                new String[] {"%"+ filter+ "%" }, null, null, null,
//                null);
        Cursor cursor = db.query(false,UsuarioContract.NOME_TABELA,colunas,UsuarioContract.COLUNA_NOME + " LIKE ?",new String[]{"%"+nome+"%"},null,null,UsuarioContract.COLUNA_NOME+" ASC",null);

        ArrayList<Usuario> contatos = new ArrayList<Usuario>();
        while(cursor.moveToNext()){
            Usuario usuario =  new Usuario();

            usuario.setId(cursor.getInt(cursor.getColumnIndex(UsuarioContract.COLUNA_ID)));
            usuario.setCpf(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_CPF)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_NOME)));
            usuario.setTelefone(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_TELEFONE)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioContract.COLUNA_EMAIL)));
            contatos.add(usuario);
        }

        cursor.close();
        return contatos;

    }
}
