package com.prices.products.productsprices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import model.Candidato;

public class DashboardActivity extends AppCompatActivity {

    private TextView textViewNome;
    private Candidato votoPrefeito;
    private Candidato votoVereador;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JSONObject jsonObject = null;

        textViewNome = (TextView) findViewById(R.id.textViewNome);

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

            try {
                token = getIntent().getStringExtra("token");
                jsonObject = new JSONObject(JWTUtils.decoded(token));
                textViewNome.setText(" " + jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            votoVereador.setFoto(null);
            i.putExtra("votoVereador", votoVereador);
        }
        if (votoPrefeito != null) {
            votoPrefeito.setFoto(null);
            i.putExtra("votoPrefeito", votoPrefeito);
        }
        if (token != null) {
            i.putExtra("token", token);
        }

        startActivity(i);
    }
}
