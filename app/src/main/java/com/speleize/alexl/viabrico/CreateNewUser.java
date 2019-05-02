package com.speleize.alexl.viabrico;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class CreateNewUser extends AppCompatActivity {


    private EditText addmail;
    private EditText addpassword;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_newuser);


        addmail = findViewById(R.id.editmail);
        addpassword = findViewById(R.id.editpassword);
        addButton = findViewById(R.id.goToHome);


        // Récupération du token :

        SharedPreferences getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
        String token = getToken.getString("token", "");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values to string :

                String strMail = addmail.getText().toString();
                String strPassword = addpassword.getText().toString();

                RequestParams params = new RequestParams();
                params.put("mail", strMail);
                params.put("password", strPassword);

                Request.post("https://viabrico.herokuapp.com/suppliers/", token, params, new TextHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("response : ", responseString);
                        Intent intent = new Intent(getApplicationContext(), CreateNewUser.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("error :", responseString);

                    }

                });
            }
        });

    }
}
