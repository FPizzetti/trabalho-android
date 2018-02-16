package com.prices.products.productsprices;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import model.Aluno;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "HomeActivity";

    private List<Aluno> alunos;

    private ListView listView;
    private SearchView search;

    public HomeActivity() {
        alunos = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.list);
        search = (SearchView) findViewById(R.id.search);
        search.setOnQueryTextListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(((ListCellAlunos) adapterView.getAdapter()).alunos.get(i));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loadData();
    }

    public void loadData() {
        new ListAlunosTask(HomeActivity.this).execute();
    }

    public void afterLoad(JSONArray jsonArray) {
        try {
            if (jsonArray != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Aluno>>() {
                }.getType();
                alunos = gson.fromJson(jsonArray.toString(), type);
                ListAdapter adapter = new ListCellAlunos(this, alunos);
                listView.setAdapter(adapter);
            } else {
                onConnectionFailed();
            }
        } catch (Exception E) {
            Toast.makeText(getBaseContext(), "Não foi possivel estabelecer conexão com o servidor.", Toast.LENGTH_LONG).show();
        }
    }

    public void onConnectionFailed() {
        Toast.makeText(getBaseContext(), "Não foi possivel estabelecer conexão com o servidor.", Toast.LENGTH_LONG).show();
    }

    private void openActivity(Aluno aluno) {
//        Intent i = new Intent(this, CandidatosActivity.class);
//            i.putExtra("votoVereador", votoVereador);
//            i.putExtra("votoPrefeito", votoPrefeito);
//        i.putExtra("candidatoSelecionado", candidato);
//        i.putExtra("tipo", 1);
//        startActivity(i);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s == null || s.equals("")) {
            ListAdapter adapter = new ListCellAlunos(this, alunos);
            listView.setAdapter(adapter);
        } else {
            List<Aluno> search = new ArrayList<>();

            for (Aluno aluno : alunos) {
                boolean matches = aluno.getId().contains(s) || aluno.getNome().contains(s) || aluno.getCpf().contains(s);
                if (matches) {
                    search.add(aluno);
                }
            }
            ListAdapter adapter = new ListCellAlunos(this, search);
            listView.setAdapter(adapter);
        }
        return false;
    }
}
