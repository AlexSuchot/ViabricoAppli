package com.speleize.alexl.viabrico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private TextView title;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.goToHome);
        registerButton = findViewById(R.id.goToRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Loading logo :
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);


                // Email and password to string :
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                if (strEmail.length() > 0) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }

                // On vérifie que les champs ne soient pas vide :
                if(     !isEmpty(strEmail) &&
                        !isEmpty(strPassword)) {

                    // Check for email validity :
                    if (isEmailValid(strEmail)) {
                        RequestParams params = new RequestParams();
                        params.put("email", strEmail);
                        params.put("password", strPassword);

                        email.setError(null);
                        password.setError(null);

                        Request.post("https://viabrico.herokuapp.com/login", null, params, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseBody) {

                                // called when response HTTP status is "200 OK"

                                Log.d("res", "code: " + statusCode);
                                Log.d("res", "headers: " + headers.toString());

                                Log.d("res", "res:" + responseBody);


                                // SHARED PREFERENCES :

                                SharedPreferences getToken;
                                getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = getToken.edit();
                                editor.putString("token", responseBody.substring(1, responseBody.length() - 1));
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, LoggedActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                                Log.d("res", "res: " + error);

                                Log.d("status code :", "status code :" + statusCode);
                                email.setError("Mauvais mail ou mot de passe !");
                                password.setError("Mauvais mail ou mot de passe !");
                            }
                        });
                    } else {
                        email.setError("Mauvais format de mail ! ");

                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs." , Toast.LENGTH_LONG).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);

            }

        });
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    public boolean isEmpty(String value){
        if(value.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

}

