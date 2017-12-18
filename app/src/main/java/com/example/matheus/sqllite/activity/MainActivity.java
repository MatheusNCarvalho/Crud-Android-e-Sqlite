package com.example.matheus.sqllite.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matheus.sqllite.R;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnNovoCadastrado;

    private static final String  PREF_NAME = "MainActivityPreferences";
    private int count1;
    private  int count2;

    private SharedPreferences.OnSharedPreferenceChangeListener callback = new SharedPreferences.OnSharedPreferenceChangeListener(){

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            Log.i("Script_Callbak ", s+" updated");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Criando o arquivo de preferencia
        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE); // Metodo da class Context
        count1 = sp1.getInt("count_1",0 );
        Log.i("Script", "Count 1:" + count1);



        sp1.registerOnSharedPreferenceChangeListener(callback);

        SharedPreferences sp2 = getPreferences( MODE_PRIVATE); //Metodo da Activity
        count2 = sp2.getInt("count_2",0 );
        Log.i("Script", "Count 2:" + count2);



//        listView = (ListView) findViewById(R.id.l)

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, ListagemUsuariosActivity.class);
                startActivity(in);

            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();

        //Criando o arquivo de preferencia
        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE); // Metodo da class Context
        count1 = sp1.getInt("count_1",0 );
        SharedPreferences.Editor editor = sp1.edit();
        editor.putInt("count_1", count1 -1);
        editor.commit();
        Log.i("Script", "Count 1" + count1);


        SharedPreferences sp2 = getPreferences( MODE_PRIVATE); // Metodo da class Context
        count2 = sp2.getInt("count_2",0 );
        editor = sp2.edit();
        editor.commit();
        editor.putInt("count_2", count2 +1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE); // Metodo da class Context
        sp1.unregisterOnSharedPreferenceChangeListener(callback);


        SharedPreferences.Editor editor = sp1.edit();
        editor.clear();
        editor.commit();
        count1 = sp1.getInt("count_1",0 );
        Log.i("Script", "Count 1: " + count1);


        SharedPreferences sp2 = getPreferences(MODE_PRIVATE);
        editor = sp2.edit();
        editor.clear();
        editor.commit();
        editor.putInt("count_2", count2 +1);

    }
}
