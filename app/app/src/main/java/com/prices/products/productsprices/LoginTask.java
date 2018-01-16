package com.prices.products.productsprices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONObject;

import dao.UsuarioDAO;

public class LoginTask extends AsyncTask<Void, Void, JSONObject> {
    private Activity activity;
    private ProgressDialog pd;
    private String login, senha;
    private JSONObject jSONObject;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginTask(Activity activity, String login, String senha) {
        this.activity = activity;
        this.login = login;
        this.senha = senha;
    }

    protected void onPreExecute() {
        pd = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
        pd.setIndeterminate(true);
        pd.setMessage("Autenticando...");
        pd.show();
    }

    @Override
    protected JSONObject doInBackground(Void... arg0) {
        try {
            jSONObject = usuarioDAO.autenticar(login, senha);
        } catch (Exception e) {

        }
        pd.dismiss();
        return jSONObject;
    }

    @Override
    protected void onPostExecute(JSONObject jSONObject) {

        super.onPostExecute(jSONObject);

        ((LoginActivity) activity).afterLogin(jSONObject);
    }

}
