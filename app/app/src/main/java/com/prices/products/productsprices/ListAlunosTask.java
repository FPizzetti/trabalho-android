package com.prices.products.productsprices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;

import dao.AlunoDAO;

public class ListAlunosTask extends AsyncTask<Void, Void, JSONArray> {
    private Activity activity;
    private ProgressDialog pd;
    private String login, senha;
    private JSONArray jsonArray;
    private AlunoDAO alunoDAO;

    public ListAlunosTask(Activity activity) {
        this.activity = activity;
        this.alunoDAO = new AlunoDAO();
    }

    protected void onPreExecute() {
        pd = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
        pd.setIndeterminate(true);
        pd.setMessage("Carregando...");
        pd.show();
    }

    @Override
    protected JSONArray doInBackground(Void... arg0) {
        try {
            jsonArray = alunoDAO.getAlunos();
        } catch (Exception e) {

        }
        pd.dismiss();
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        ((HomeActivity) activity).afterLoad(jsonArray);
    }
}
