package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DAO {

    private OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String API_URL =
            "http://192.168.1.19:8084/api-web/api/";
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public DAO() {
        client = new OkHttpClient();
    }

    public String doGet(String url) throws IOException {

        Request request = new Request.Builder()
                .url(API_URL + url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPost(String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(API_URL + url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPut(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(API_URL + url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doDelete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(API_URL + url)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = gson.fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
}

