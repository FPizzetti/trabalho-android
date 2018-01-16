package com.prices.products.productsprices;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import model.Candidato;

public class CandidatosActivity extends AppCompatActivity {

    private Candidato votoPrefeito;
    private Candidato votoVereador;
    private Candidato candidatoSelecionado;
    private String token;

    private TextView nomeCandidato;
    private TextView partidoCandidato;
    private ImageView imageView;

    private Button confirmar;

    private int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos);

        nomeCandidato = (TextView) findViewById(R.id.nomeCandidato);
        partidoCandidato = (TextView) findViewById(R.id.partidoCandidato);
        imageView = (ImageView) findViewById(R.id.imageView);

        confirmar = (Button) findViewById(R.id.confirmar);

        if (getIntent() != null) {
            if (getIntent().getSerializableExtra("votoPrefeito") != null) {
                votoPrefeito = (Candidato) getIntent().getSerializableExtra("votoPrefeito");
            }
            if (getIntent().getSerializableExtra("candidatoSelecionado") != null) {
                candidatoSelecionado = (Candidato) getIntent().getSerializableExtra("candidatoSelecionado");
                tipo = getIntent().getIntExtra("tipo", 0);
                nomeCandidato.setText(candidatoSelecionado.getNome());
                partidoCandidato.setText(candidatoSelecionado.getPartido());
            }
            if (getIntent().getSerializableExtra("votoVereador") != null) {
                votoVereador = (Candidato) getIntent().getSerializableExtra("votoVereador");
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
        //if (candidatoSelecionado != null && candidatoSelecionado.getFotoUrl() != null)
        new DownloadImage(this, candidatoSelecionado.getFotoUrl()).execute();
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
            i.putExtra("tipo", 2);
        }
        if (votoPrefeito != null) {
            votoPrefeito.setFoto(null);
            i.putExtra("votoPrefeito", votoPrefeito);
            i.putExtra("tipo", 1);
        }
        if (token != null) {
            i.putExtra("token", token);
        }
        startActivity(i);
    }

    private void openActivity(Candidato candidato) {
        Intent i = new Intent(this, CandidatosActivity.class);
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
        String message = "";
        if (tipo == 1) {
            votoPrefeito = (Candidato) getIntent().getSerializableExtra("candidatoSelecionado");
            message = "Prefeito selecionado com sucesso";
        } else {
            votoVereador = (Candidato) getIntent().getSerializableExtra("candidatoSelecionado");
            message = "Vereador selecionado com sucesso";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public void onAfterBuscarImagem(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }


}
