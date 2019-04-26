package com.speleize.alexl.viabrico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.goToHome);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaType JSON = MediaType.get("application/json; charset=utf-8");
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
                AsynchronousGet asynchronousGet = new AsynchronousGet("https://viabrico.herokuapp.com/register", "{'email':'" + strEmail + "','password':'" + strPassword + "'}");
                try {
                    asynchronousGet.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
