package com.prices.products.productsprices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.Candidato;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String url;


    public DownloadImage(Activity activity, String url) {
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity, "Por favor aguarde...", "Buscando dados...");
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            return (Utils.baixarImagem(url));
        } catch (Exception e) {
            e.printStackTrace();
            handleError("Erro ao ler dados");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog.dismiss();
        ((CandidatosActivity) activity).onAfterBuscarImagem(bitmap);
    }

    private void handleError(final String message) {
        ((CandidatosActivity) activity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
