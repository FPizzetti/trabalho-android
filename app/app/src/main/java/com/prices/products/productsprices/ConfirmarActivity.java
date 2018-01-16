package com.prices.products.productsprices;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.Candidato;

public class ConfirmarActivity extends AppCompatActivity {

    private Candidato votoPrefeito;
    private Candidato votoVereador;
    private String token;

    private TextView nomePrefeito;
    private TextView partidoPrefeito;

    private TextView nomeVereador;
    private TextView partidoVereador;

    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        nomePrefeito = (TextView) findViewById(R.id.nomeCandidato);
        partidoPrefeito = (TextView) findViewById(R.id.partidoCandidato);

        nomeVereador = (TextView) findViewById(R.id.nomeVereador);
        partidoVereador = (TextView) findViewById(R.id.partidoVereador);

        confirmar = (Button) findViewById(R.id.confirmar);

        if (getIntent() != null) {
            if (getIntent().getSerializableExtra("votoPrefeito") != null) {
                votoPrefeito = (Candidato) getIntent().getSerializableExtra("votoPrefeito");
                nomePrefeito.setText(votoPrefeito.getNome());
                partidoPrefeito.setText(votoPrefeito.getPartido());
            } else {
                nomePrefeito.setText("Voto nulo");
                Toast.makeText(this, "Vote em um prefeito", Toast.LENGTH_LONG);
                confirmar.setEnabled(false);
            }
            if (getIntent().getSerializableExtra("votoVereador") != null) {
                votoVereador = (Candidato) getIntent().getSerializableExtra("votoVereador");
                nomeVereador.setText(votoVereador.getNome());
                partidoVereador.setText(votoVereador.getPartido());
            } else {
                nomeVereador.setText("Voto nulo");
                Toast.makeText(this, "Vote em um vereador", Toast.LENGTH_LONG);
                confirmar.setEnabled(false);
            }
            if (getIntent().getStringExtra("token") != null) {
                token = getIntent().getStringExtra("token");
            }
        }

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                votar();
            }
        });

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

    private void votar() {
        new VotarTask(this, token, votoVereador, votoPrefeito).execute();
    }

    public void onAfterVotar(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarActivity.this);
        builder.setTitle("Sucesso");
        builder.setMessage(message);
        builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openActivity(DashboardActivity.class);
            }
        });
        builder.create().show();


    }


}
