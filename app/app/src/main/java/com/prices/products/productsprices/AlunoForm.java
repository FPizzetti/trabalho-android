package com.prices.products.productsprices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONObject;

import dao.AlunoDAO;
import model.Aluno;
import model.Endereco;


public class AlunoForm extends AppCompatActivity {

    private String id = null;
    private int enderecoId = 0;

    private AlunoDAO alunoDAO;

    private Button salvar;
    private Button cancelar;

    private TextView grr;
    private EditText estado;
    private EditText cpf;
    private EditText idade;
    private EditText nome;
    private EditText logradouro;
    private EditText numero;
    private EditText complemento;
    private EditText bairro;
    private EditText cep;
    private EditText cidade;


    public AlunoForm() {
        alunoDAO = new AlunoDAO();
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
                remove();
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
                fab.setVisibility(View.VISIBLE);
                grr.setText(this.id);
                try {
                    JSONObject obj = alunoDAO.getByMatricula(this.id);
                    cpf.setText(obj.getString("cpf"));
                    idade.setText(obj.getString("idade"));
                    nome.setText(obj.getString("nome"));
                    JSONObject end = obj.getJSONObject("endereco");
                    this.enderecoId = end.getInt("id");
                    logradouro.setText(end.getString("logradouro"));
                    numero.setText(end.getString("numero"));
                    complemento.setText(end.getString("complemento"));
                    bairro.setText(end.getString("bairro"));
                    cep.setText(end.getString("cep"));
                    cidade.setText(end.getString("cidade"));
                    estado.setText(end.getString("estado"));
                } catch (Exception e) {
                    this.showToast("Não foi possível recuperar os dados. Tente novamente mais tarde");
                }
            } else {
                fab.setVisibility(View.INVISIBLE);
                grr.setText("Novo Aluno");
            }
        }
    }

    private void loadComponents() {

        salvar = (Button) findViewById(R.id.btn_salvar);
        cancelar = (Button) findViewById(R.id.btn_cancelar);

        grr = (TextView) findViewById(R.id.grr);
        cpf = (EditText) findViewById(R.id.input_cpf);
        idade = (EditText) findViewById(R.id.input_idade);
        nome = (EditText) findViewById(R.id.input_nome);

        logradouro = (EditText) findViewById(R.id.input_logradouro);
        numero = (EditText) findViewById(R.id.input_numero);
        complemento = (EditText) findViewById(R.id.input_complemento);
        bairro = (EditText) findViewById(R.id.input_bairro);
        cep = (EditText) findViewById(R.id.input_cep);
        cidade = (EditText) findViewById(R.id.input_cidade);
        estado = (EditText) findViewById(R.id.input_estado);
        estado.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void cancel() {
        finish();
    }

    private void save() {
        try {
            Endereco e = new Endereco(enderecoId, logradouro.getText().toString(), Integer.parseInt(numero.getText().toString()), complemento.getText().toString(), bairro.getText().toString(), cep.getText().toString(), cidade.getText().toString(), estado.getText().toString());
            Aluno a = new Aluno(this.id, cpf.getText().toString(), nome.getText().toString(), Integer.parseInt(idade.getText().toString()), e);
            new SaveAlunoTask(a, this).execute();
        } catch (Exception e) {
            Toast.makeText(this, "Verifique as informações antes de salvar", Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void remove() {
        new DeleteAlunoTask(this.id, this).execute();
    }

    private class SaveAlunoTask extends AsyncTask<String, Void, String> {

        private Activity activity;
        private ProgressDialog pd;
        private Aluno aluno;

        public SaveAlunoTask(Aluno aluno, Activity activity) {
            this.aluno = aluno;
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                alunoDAO.salvar(aluno);
            } catch (Exception e) {
//                ((AlunoForm) activity).showToast("Não foi possível salvar o aluno");
            }
            pd.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ((AlunoForm) activity).showToast("Aluno salvo com sucesso");
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
            pd.setIndeterminate(true);
            pd.setMessage("Salvando...");
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class DeleteAlunoTask extends AsyncTask<String, Void, String> {

        private Activity activity;
        private ProgressDialog pd;
        private String id;

        public DeleteAlunoTask(String id, Activity activity) {
            this.id = id;
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                alunoDAO.removeById(this.id);
                activity.finish();
            } catch (Exception e) {
//                ((AlunoForm) activity).showToast("Não foi possivel remover. Tente novamente mais tarde");
            }
            pd.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ((AlunoForm) activity).showToast("Aluno removido com sucesso");
            activity.finish();
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
            pd.setIndeterminate(true);
            pd.setMessage("Removendo...");
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
