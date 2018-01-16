package com.prices.products.productsprices;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dao.DAO;
import model.Candidato;

public class Utils {

    public static String PREFEITOS_URL = "https://dl.dropboxusercontent.com/u/40990541/prefeito.json";
    public static String VEREADORES_URL = "https://dl.dropboxusercontent.com/u/40990541/vereador.json";
    public static String VOTOS_URL = "http://192.168.1.6:8000/votos?token=";

    public static Bitmap baixarImagem(String url) throws IOException {
        URL endereco;
        InputStream inputStream;

        Bitmap bitmap;

        endereco = new URL(url);
        inputStream = endereco.openStream();
        bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    public static String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String votar(String token, Candidato vereador, Candidato prefeito) {
        String response = null;

        String votoPrefeito = (prefeito != null ? prefeito.getId() : "null");
        String votoVereador = (vereador != null ? vereador.getId() : "null");
        String json = "{" + "\"mayor_id\":" + votoPrefeito + "," +
                "\"councilman_id\":" + votoVereador + "}";

        DAO dao = new DAO();
        try {
            response = dao.doPost("votes?token=" + token, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder builder = new StringBuilder();
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public static Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
