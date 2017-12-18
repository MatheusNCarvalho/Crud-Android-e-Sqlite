package com.example.matheus.sqllite.configuracao;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 09/11/2017.
 */

public final class ContatoContract implements BaseColumns {

    private ContatoContract(){}

    public static  final String NOME_TABELA ="contatos";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_NOME ="nome";
    public static  final String COLUNA_TELEFONE ="telefone";
    public static  final String COLUNA_INFO ="info";

}
