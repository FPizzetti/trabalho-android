package com.prices.products.productsprices;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import model.Candidato;

public class PrefeitoActivity extends AppCompatActivity {

    private ListView listView;

    private Candidato votoPrefeito;
    private Candidato votoVereador;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefeito);

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(((ListCellCandidatos) adapterView.getAdapter()).candidatos.get(i));
            }
        });

        if (getIntent() != null) {
            if (getIntent().getSerializableExtra("votoPrefeito") != null) {
                votoPrefeito = (Candidato) getIntent().getSerializableExtra("votoPrefeito");
            }
            if (getIntent().getSerializableExtra("votoVereador") != null) {
                votoVereador = (Candidato) getIntent().getSerializableExtra("votoVereador");
            }
            if (getIntent().getStringExtra("token") != null) {
                token = getIntent().getStringExtra("token");
            }
        }

        new DownloadTask(this, Utils.PREFEITOS_URL, listView).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vereador:
                openActivity(VereadorActivity.class);
                return true;
            case R.id.prefeito:
                openActivity(PrefeitoActivity.class);
                return true;
            case R.id.dashboard:
                openActivity(DashboardActivity.class);
                return true;
            case R.id.confirmar:
                openActivity(ConfirmarActivity.class);
                return true;
            case R.id.sair:
                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openActivity(Class activity) {
        Intent i = new Intent(this, activity);
        if (votoVereador != null) {
            i.putExtra("votoVereador", votoVereador);
        }
        if (votoPrefeito != null) {
            i.putExtra("votoPrefeito", votoPrefeito);
        }
        if (token != null) {
            i.putExtra("token", token);
        }
        startActivity(i);
    }

    private void openActivity(Candidato candidato) {
        Intent i = new Intent(this, CandidatosActivity.class);
        if (votoVereador != null) {
            i.putExtra("votoVereador", votoVereador);
        }
        if (votoPrefeito != null) {
            i.putExtra("votoPrefeito", votoPrefeito);
        }
        if (token != null) {
            i.putExtra("token", token);
        }
        i.putExtra("candidatoSelecionado", candidato);
        i.putExtra("tipo", 1);
        startActivity(i);
    }


}
