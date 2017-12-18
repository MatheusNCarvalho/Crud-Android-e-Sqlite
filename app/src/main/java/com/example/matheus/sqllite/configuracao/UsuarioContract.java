package com.example.matheus.sqllite.configuracao;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 11/11/2017.
 */

public final class UsuarioContract implements BaseColumns {
    private UsuarioContract(){

    }

    public static  final String NOME_TABELA ="usuarios";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_NOME ="nome";
    public static  final String COLUNA_EMAIL ="email";
    public static  final String COLUNA_CPF ="cpf";
    public static  final String COLUNA_TELEFONE ="telefone";

}
