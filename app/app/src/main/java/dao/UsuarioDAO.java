package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import model.Usuario;

public class UsuarioDAO {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private DAO dao = new DAO();

    public JSONObject autenticar(String titulo, String senha) {
        Usuario usuario = new Usuario();
        usuario.setIdentification(titulo);
        usuario.setPassword(senha);
        try {
            Gson json = new Gson();
            String loginData = dao.doPost("/auth", json.toJson(usuario));
            JSONObject jObject = new JSONObject(loginData);

            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
