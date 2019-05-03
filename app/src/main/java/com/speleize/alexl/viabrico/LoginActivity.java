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

                // On vérifie que les champs ne soient pas vide :
                if(     !isEmpty(strEmail) &&
                        !isEmpty(strPassword)) {

                    // On vérifie que l'email est valide :
                    if (isEmailValid(strEmail)) {
                        RequestParams params = new RequestParams();
                        params.put("email", strEmail);
                        params.put("password", strPassword);

                        email.setError(null);
                        password.setError(null);

                        // On fait une requête post sur la route /login :
                        Request.post("https://viabrico.herokuapp.com/login", null, params, new TextHttpResponseHandler() {
                            @Override
                            // Si il y a réussite (status 200) :
                            public void onSuccess(int statusCode, Header[] headers, String responseBody) {

                                // On affiche dans des logs les informations retourné par la requête :

                                Log.d("res", "code: " + statusCode);
                                Log.d("res", "headers: " + headers.toString());
                                Log.d("res", "res:" + responseBody);

                                // SHARED PREFERENCES  :
                                // On sauvegarde le token dans un shared preferences :
                                SharedPreferences getToken;
                                getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = getToken.edit();
                                editor.putString("token", responseBody.substring(1, responseBody.length() - 1));
                                editor.apply();

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                                Intent intent = new Intent(LoginActivity.this, LoggedActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                                Log.d("res", "res: " + error);
                                Log.d("status code :", "status code :" + statusCode);

                                email.setError("Mauvais mail ou mot de passe !");
                                password.setError("Mauvais mail ou mot de passe !");

                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            }
                        });
                    } else {
                        email.setError("Mauvais format de mail ! ");

                    }
                } else {
                    // On informe à l'utilisateur qu'il reste des champs à remplir :
                    Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs." , Toast.LENGTH_LONG).show();
                }
            }
        });

        // OnClickListener --> éxécute une action lors du clique sur le bouton avec l'id "registerButton" :
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    // Fonction avec un régex qui vérifie qu'une string est un email :
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

    // Fonction qui retourne "true" si la valeur est vide, pour checker que tous les champs soient rempli :
    public boolean isEmpty(String value){
        if(value.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

}

