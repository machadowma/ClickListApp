package com.github.machadowma.clicklistapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase bancoDados;
    public ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCadastro();
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        criarBancoDados();
        listarDados();;
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarDados();;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("clicklist", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoa(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , nome VARCHAR" +
                    " ) " );
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirCadastro(){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    private void listarDados() {
        try {
            bancoDados = openOrCreateDatabase("clicklist", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id,nome FROM pessoa", null);
            ArrayList<Pessoa> pessoasArray = new ArrayList<Pessoa>();
            CustomListAdapter customListAdapter = new CustomListAdapter(this, pessoasArray);
            if(cursor.moveToFirst()) {
                do {
                    Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                    String nome = cursor.getString(cursor.getColumnIndex("nome"));
                    Pessoa pessoa = new Pessoa(id,nome);
                    pessoasArray.add(pessoa);
                } while (cursor.moveToNext());
            }
            listView.setAdapter(customListAdapter);
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
