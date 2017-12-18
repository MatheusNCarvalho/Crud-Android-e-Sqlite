package com.example.matheus.sqllite.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.sqllite.DAO.PreferenciaDAO;
import com.example.matheus.sqllite.R;
import com.example.matheus.sqllite.model.Usuario;
import com.example.matheus.sqllite.DAO.UsuarioDAO;

import java.util.HashSet;


public class CadUsuariosActivity extends AppCompatActivity {

    private static final String PREF_NAME = "CadUsuariosPreferences";

    private EditText editNome, editCPF, editEmail, editTelefone;
    private Button btnSalvarEditar, btnApagar;
    private CheckBox checkPereferencia;
    private PreferenciaDAO preferenciaDAO;

    Usuario usuario, altusuario;
    UsuarioDAO usuarioDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuarios);


        Intent i = getIntent();

        altusuario = (Usuario) i.getSerializableExtra("pessoa-enviada");

        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO(this);

        editNome = (EditText) findViewById(R.id.editNome);
        editCPF = (EditText) findViewById(R.id.editCpf);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        btnApagar = (Button) findViewById(R.id.btnDelete);
        checkPereferencia = (CheckBox) findViewById(R.id.chekPreferencia);


        btnSalvarEditar = (Button) findViewById(R.id.btnSalvar);


        if (altusuario != null) {
            btnSalvarEditar.setText("Alterar");
            btnApagar.setVisibility(View.VISIBLE);

            editNome.setText(altusuario.getNome());
            editCPF.setText(altusuario.getCpf());
            editEmail.setText(altusuario.getEmail());
            editTelefone.setText(altusuario.getTelefone());

            usuario.setId(altusuario.getId());
        } else {
            btnSalvarEditar.setText("Salvar");
        }


        btnSalvarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setNome(editNome.getText().toString());
                usuario.setEmail(editEmail.getText().toString());
                usuario.setCpf(editCPF.getText().toString());
                usuario.setTelefone(editTelefone.getText().toString());


                if (usuario.getId() == null) {
                    if (checkPereferencia.isChecked())
                        salvarPreferencia(usuario);


                    Boolean retorno = usuarioDAO.save(usuario);

                    if (retorno) {
                        Toast.makeText(CadUsuariosActivity.this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CadUsuariosActivity.this, "Error ao salvar", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if (checkPereferencia.isChecked())
                        salvarPreferencia(usuario);

                    Boolean retorno = usuarioDAO.update(usuario);

                    if (retorno) {
                        Toast.makeText(CadUsuariosActivity.this, "Error ao salvar", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(CadUsuariosActivity.this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();


            }
        });

        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apagarPreferencia(usuario.getNome());

                boolean retorno = usuarioDAO.delete(usuario.getId());

                if (retorno) {
                    Toast.makeText(CadUsuariosActivity.this, "Usuario Apagado com Sucesso", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(CadUsuariosActivity.this, "Erro ao Apagar Usuario", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }


    public void apagarPreferencia(String nome) {
        SharedPreferences usuarioPre = getSharedPreferences(PREF_NAME, MODE_PRIVATE); // Metodo da class Context
        SharedPreferences.Editor editor = usuarioPre.edit();
        editor.remove(nome);
        editor.commit();
    }


    public void salvarPreferencia(Usuario usuario) {

        //Criando o arquivo de preferencia
        SharedPreferences usuarioPre = getSharedPreferences(PREF_NAME, MODE_PRIVATE); // Metodo da class Context
        SharedPreferences.Editor editor = usuarioPre.edit();

        HashSet<String> set = new HashSet<String>();
        set.add(usuario.getEmail());
        set.add(usuario.getCpf());
        set.add(usuario.getTelefone());
        editor.putStringSet(usuario.getNome(), set);
//        editor.putString("Nome", usuario.getNome());
//        editor.putString("Email", usuario.getEmail());
//        editor.putString("Telefone", usuario.getTelefone());
//        editor.putString("Cpf", usuario.getCpf());


        Boolean retorno = editor.commit();

        if (retorno) {
            Log.i("Script", "Salvo com Sucesso");
        } else {
            Log.i("Script", "Erro ao salvar");
        }


//        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//
//// We need an editor object to make changes
//        SharedPreferences.Editor edit = pref.edit();
//
//// Set/Store data
//        HashSet<String> set = new HashSet<String>();
//        set.add("Rich Dad Poor DaD");
//        set.add("The Cold Steel");
//        set.add("Steve Jobs Biography");
//        edit.putStringSet("books", set);
//
//// Commit the changes
//        edit.commit();

    }


}
