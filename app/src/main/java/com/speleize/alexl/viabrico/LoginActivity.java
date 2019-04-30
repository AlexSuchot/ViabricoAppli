package com.speleize.alexl.viabrico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private TextView title;

    private String url = "https://viabrico.herokuapp.com/login";

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

                // Email and password to string :
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                AsyncHttpClient client = new AsyncHttpClient();
                client.setConnectTimeout(60000);
                client.setResponseTimeout(60000);
                RequestParams params = new RequestParams();
                params.put("email", strEmail);
                params.put("password", strPassword);
                client.post("https://viabrico.herokuapp.com/login", params, new TextHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                        // called when response HTTP status is "200 OK"
                        Log.d("res", "code: " + statusCode);
                        Log.d("res", "headers: " + headers.toString());

                        Log.d("res", "res:" + responseBody);
                        Log.d("res", "Ah shit here we go again:" + responseBody);

                        Intent intent = new Intent(LoginActivity.this, LoggedActivity.class);

                        intent.putExtra("token :", responseBody);
                        LoginActivity.this.startActivity(intent);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                        Log.d("res", "res: " + error);

                        Log.d("status code :", "status code :" + statusCode);
                    }
                });

            }
        });
    }
}
