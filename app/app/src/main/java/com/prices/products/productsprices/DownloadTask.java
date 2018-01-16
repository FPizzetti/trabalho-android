package com.prices.products.productsprices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.Candidato;

public class DownloadTask extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String url;
    private List<Candidato> candidatos;
    private ListView listView;

    public DownloadTask(Activity activity, String url, ListView listView) {
        this.activity = activity;
        this.url = url;
        candidatos = new ArrayList<>();
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity, "Por favor aguarde...", "Buscando dados...");
    }

    @Override
    protected Void doInBackground(String... strings) {
        String json = Utils.makeServiceCall(url);

        if (json != null) try {
            JSONObject object = new JSONObject(json);
            JSONArray candidatosJson;
            if (url.contains("prefeito.json")) {
                candidatosJson = object.getJSONArray("prefeito");
            } else {
                candidatosJson = object.getJSONArray("vereador");
            }

            for (int i = 0; i < candidatosJson.length(); i++) {
                JSONObject t = candidatosJson.getJSONObject(i);

                Candidato candidato = new Candidato();
                candidato.setId(t.getString("id"));
                candidato.setNome(t.getString("nome"));
                candidato.setPartido(t.getString("partido"));
                candidato.setFoto(Utils.baixarImagem(t.getString("foto")));
                candidato.setFotoUrl(t.getString("foto"));
                candidatos.add(candidato);
            }

        } catch (Exception e) {
            e.printStackTrace();
            handleError("Erro ao ler dados");
        }
        else {
            handleError("Erro ao buscar dados");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void bitmap) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        ListAdapter adapter = new ListCellCandidatos(activity, candidatos);
        listView.setAdapter(adapter);

        progressDialog.dismiss();
    }

    private void handleError(final String message) {
        if (activity instanceof PrefeitoActivity) {
            ((PrefeitoActivity) activity).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ((VereadorActivity) activity).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
