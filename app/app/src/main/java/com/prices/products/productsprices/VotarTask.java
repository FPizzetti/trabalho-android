package com.prices.products.productsprices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import model.Candidato;

public class VotarTask extends AsyncTask<Void, Void, String> {
    private Activity activity;
    private ProgressDialog pd;
    private Candidato vereador, prefeito;
    private String token;

    public VotarTask(Activity activity, String token, Candidato vereador, Candidato prefeito) {
        this.activity = activity;
        this.vereador = vereador;
        this.token = token;
        this.prefeito = prefeito;
    }

    protected void onPreExecute() {
        pd = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
        pd.setIndeterminate(true);
        pd.setMessage("Computando Voto...");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... arg0) {
        String response = null;
        try {
            response = Utils.votar(token, vereador, prefeito);
            if (response != null) {
                response = "Voto computado com sucesso";
            } else {
                response = "Erro ao computar voto";
            }
        } catch (Exception e) {
            response = "Erro ao computar voto";
        }
        pd.dismiss();
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ((ConfirmarActivity) activity).onAfterVotar(s);
    }
}