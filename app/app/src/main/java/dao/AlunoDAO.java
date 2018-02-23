package dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Aluno;

public class AlunoDAO {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private DAO dao = new DAO();

    public JSONArray getAlunos() {
        try {
            String data = dao.doGet("/alunos");
            JSONArray array = new JSONArray(data);
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getByMatricula(String id) {
        try {
            String data = dao.doGet("/alunos/" + id);
            JSONObject jObject = new JSONObject(data);
            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject salvar(Aluno a) {
        try {
            Gson json = new Gson();
            String data = "";
            if (a.getId() != null) {
                data = dao.doPut("/alunos/" + a.getId(), json.toJson(a));
            } else {
                data = dao.doPost("/alunos", json.toJson(a));
            }
            JSONObject jObject = new JSONObject(data);
            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject removeById(String id) {
        try {
            String data = dao.doDelete("/alunos/" + id);
            JSONObject jObject = new JSONObject(data);
            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
