package com.speleize.alexl.viabrico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    private EditText addmail;
    private EditText addpassword;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addmail = findViewById(R.id.addmail);
        addpassword = findViewById(R.id.addpassword);
        registerButton = findViewById(R.id.register);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values to string :
                String strPassword = addpassword.getText().toString();
                String strMail = addmail.getText().toString();

                if (!isEmpty(strMail) && (!isEmpty(strPassword))) {


                    // On vérifie que strMail est bien un mail :
                    if (isEmailValid(strMail)) {
                        addmail.setError(null);

                        // On récupére les paramètres à passer à la requête :
                        RequestParams params = new RequestParams();
                        params.put("email", strMail);
                        params.put("password", strPassword);

                        Request.post("https://viabrico.herokuapp.com/register", null, params, new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Log.d("response : ", responseString);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Log.d("error :", responseString);
                            }

                        });
                    } else {
                        addmail.setError("Email non valide !");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs.", Toast.LENGTH_LONG).show();
                }
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

    public boolean isEmpty(String value) {
        if (value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}