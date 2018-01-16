package com.prices.products.productsprices;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Bind;
import dao.UsuarioDAO;
import model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    String messageLogin;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            erroAoRealizarLogin(messageLogin);
            return;
        }

        _loginButton.setEnabled(false);

        String email = _emailText.getText().toString();
        String senha = _passwordText.getText().toString();

        new LoginTask(LoginActivity.this, email, senha).execute();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        messageLogin = "";

        if (email.isEmpty()) {
            messageLogin = "Informe um título.";
            valid = false;
        }
        if (password.isEmpty()) {
            if (messageLogin.isEmpty())
                messageLogin = "Informe uma senha.";
            else
                messageLogin = "\nInforme uma senha.";
            valid = false;
        }

        return valid;
    }

    public void onLoginSuccess(String token) {
        Toast.makeText(this, "Seja Bem vindo", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);

        Intent it = new Intent(this, DashboardActivity.class);
        it.putExtra("token", token);
        setResult(RESULT_OK, it);
        startActivity(it);
        this.finish();
    }

    public void afterLogin(JSONObject jSONObject) {
        try {
            if (jSONObject != null) {
                try {
                    onLoginSuccess(jSONObject.getString("token"));
                } catch (Exception e) {
                    erroAoRealizarLogin("Título e/ou senha incorretos");
                }
            } else {
                onConnectionFailed();
            }
        } catch (Exception E) {
            Toast.makeText(getBaseContext(), "Não foi possivel estabelecer conexão com o servidor.", Toast.LENGTH_LONG).show();
        }
    }

    public void onConnectionFailed() {
        Toast.makeText(getBaseContext(), "Não foi possivel estabelecer conexão com o servidor.", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    private void erroAoRealizarLogin(String erro) {
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
    }
}
