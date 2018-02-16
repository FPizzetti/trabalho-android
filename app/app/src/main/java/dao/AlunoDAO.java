package dao;

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
//            String data = dao.doGet("/alunos");
            JSONArray array = new JSONArray("[{\n" +
                    "\tid: \"grr20142291\",\n" +
                    "\tcpf: \"09489264922\",\n" +
                    "\tnome: \"felipe pizzetti\",\n" +
                    "\tidade: 15,\n" +
                    "\tendereco: {\n" +
                    "\t\tlogradouro:\"rua das flores\",\n" +
                    "\t\tnumero:13,\n" +
                    "\t\tcomplemento:\"casa\",\n" +
                    "\t\tbairro:\"pinehirinho\",\n" +
                    "\t\tcep:\"81870290\",\n" +
                    "\t\tcidade:\"curitiba\",\n" +
                    "\t\testado:\"pr\"\n" +
                    "\t}\n" +
                    "}]\n");
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getByMatricula(int id) {
        try {
//            String data = dao.doGet("/alunos/" + id);
            JSONObject jObject = new JSONObject("{\n" +
                    "\tid: \"grr20142291\",\n" +
                    "\tcpf: \"09489264922\",\n" +
                    "\tnome: \"felipe pizzetti\",\n" +
                    "\tidade: 15,\n" +
                    "\tendereco: {\n" +
                    "\t\tlogradouro:\"rua das flores\",\n" +
                    "\t\tnumero:13,\n" +
                    "\t\tcomplemento:\"casa\",\n" +
                    "\t\tbairro:\"pinehirinho\",\n" +
                    "\t\tcep:\"81870290\",\n" +
                    "\t\tcidade:\"curitiba\",\n" +
                    "\t\testado:\"pr\"\n" +
                    "\t}\n" +
                    "}");
            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject salvar(Aluno aluno) {
        try {
            Gson json = new Gson();
            String data = "";
            if (aluno.getId() != null) {
                data = dao.doPut("/alunos/" + aluno.getId(), json.toJson(aluno));
            } else {
                data = dao.doPost("/alunos", json.toJson(aluno));
            }
            JSONObject jObject = new JSONObject(data);
            return jObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject removeById(int id) {
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
