package com.example.matheus.sqllite.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matheus.sqllite.R;
import com.example.matheus.sqllite.model.Usuario;
import com.example.matheus.sqllite.DAO.UsuarioDAO;

import java.util.ArrayList;
import java.util.Map;

public class ListagemUsuariosActivity extends AppCompatActivity {

    private static final String  PREF_NAME = "CadUsuariosPreferences";

    private ListView listUsuarios;
    private Button btnNovoCdastado, btnBuscar;
    private EditText editPesquisar;

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    ArrayList<Usuario> usuariosArrayList;
    ArrayAdapter<Usuario> usuarioArrayAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_usuarios);


        listUsuarios = (ListView) findViewById(R.id.listUsuarios);
        btnNovoCdastado = (Button) findViewById(R.id.btnNovoCadastrado);
        btnBuscar = (Button) findViewById(R.id.btnPesquisar) ;

        btnNovoCdastado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListagemUsuariosActivity.this, CadUsuariosActivity.class);
                startActivity(intent);
            }
        });

        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Usuario usuarioEdidar = (Usuario) usuarioArrayAdapter.getItem(i);

                Intent im = new Intent(ListagemUsuariosActivity.this, CadUsuariosActivity.class);

                im.putExtra("pessoa-enviada",usuarioEdidar);
                startActivity(im);

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPesquisar = (EditText) findViewById(R.id.editPesquisar);

                String nomeBusca = editPesquisar.getText().toString();
                usuariosArrayList.clear();
                usuariosArrayList =  usuarioDAO.buscarPorNome(nomeBusca);

                if(listUsuarios != null){
                    usuarioArrayAdapter = new ArrayAdapter<Usuario>(ListagemUsuariosActivity.this, android.R.layout.simple_list_item_1, usuariosArrayList);
                    listUsuarios.setAdapter(usuarioArrayAdapter);
                }
            }
        });



    }



    @Override
    protected void onResume() {
        super.onResume();
        carregarUsuario();
        carregarPreferencia();
    }

    public  void carregarUsuario(){
        usuarioDAO = new UsuarioDAO(this);

        usuariosArrayList = usuarioDAO.buscarTodos();

        if(listUsuarios != null){
            usuarioArrayAdapter = new ArrayAdapter<Usuario>(ListagemUsuariosActivity.this, android.R.layout.simple_list_item_1, usuariosArrayList);
            listUsuarios.setAdapter(usuarioArrayAdapter);
        }

    }



    public  void carregarPreferencia(){

        SharedPreferences usuariosList = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        Map<String,?> ListUsuarios = usuariosList.getAll();
        for(Map.Entry<String,?> entry : ListUsuarios.entrySet()){
            Log.i("Script", entry.getKey() + " : " + entry.getValue().toString());
        }


//        Map<String, ?> allEntries = prefA.getAll();
//        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
//        }


//        Log.i("Script", "ListagemAct" + count1);
//        //Criando o arquivo de preferencia
//        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE); // Metodo da class Context
//        count1 = sp1.getInt("count_1",0 );
//        Log.i("Script", "Count 1: " + count1);
//
//
//
//        SharedPreferences sp2 = getPreferences( MODE_PRIVATE); //Metodo da Activity
//        count2 = sp2.getInt("count_2",0 );
//        Log.i("Script", "Count 2: " + count2);

    }
}
