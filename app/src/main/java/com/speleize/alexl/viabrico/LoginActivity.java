package com.speleize.alexl.viabrico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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

        loginButton.setOnClickListener((View v) -> {
            String strEmail = email.getText().toString();
            String strPassword = password.getText().toString();
            RequestParams params = new RequestParams();
            params.put("email", strEmail);
            params.put("password", strPassword);
            Request.post("login", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("res", "token: "+ response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String error, Throwable e) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    Log.d("res", "error: "+ error);
                }
            });
        });
    }
}
