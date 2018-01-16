package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Voto;

public class VotoDAO {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private DAO dao = new DAO();

    public JSONObject votar(String token) {
        try {
            String jsonString = dao.doPost("/votos?token=" + token, "");
            return new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}