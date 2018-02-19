package com.prices.products.productsprices;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dao.AlunoDAO;
import model.Aluno;


public class AlunoForm extends AppCompatActivity {

    private String id = null;

    private AlunoDAO alunoDAO;

    private Button salvar;
    private Button cancelar;

    private TextView grr;
    private EditText cpf;
    private EditText estado;


    public AlunoForm() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadComponents();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //TODO Remove with confirmation
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            this.id = intent.getStringExtra("id");
            if (id != null && !"".equals(id)) {
                grr.setText(this.id);
                //TODO load data from API
            } else {
                grr.setText("Novo Aluno");
            }
        }
    }

    private void loadComponents() {

        salvar = (Button) findViewById(R.id.btn_salvar);
        cancelar = (Button) findViewById(R.id.btn_cancelar);

        grr = (TextView) findViewById(R.id.grr);
        cpf = (EditText) findViewById(R.id.input_cpf);

        estado = (EditText) findViewById(R.id.input_estado);
        estado.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void cancel() {
        finish();
    }

    private void save() {
//        Aluno a = new Aluno();
        //TODO set fields
    }
}
