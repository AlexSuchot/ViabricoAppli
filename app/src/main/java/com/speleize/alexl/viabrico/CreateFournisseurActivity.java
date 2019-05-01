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

public class CreateFournisseurActivity extends AppCompatActivity {


    private EditText addname;
    private EditText addaddress;
    private EditText addphone;
    private EditText adddescription;
    private EditText addmail;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fournisseur);

        addname = findViewById(R.id.addname);
        addaddress = findViewById(R.id.addaddress);
        addphone = findViewById(R.id.addphone);
        adddescription = findViewById(R.id.adddescription);
        addmail = findViewById(R.id.addmail);
        addButton = findViewById(R.id.create);


        // Récupération du token :

        SharedPreferences getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
        String token = getToken.getString("token", "");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values to string :
                String strName = addname.getText().toString();
                String strAddress = addaddress.getText().toString();
                String strPhone = addphone.getText().toString();
                String strDescription = adddescription.getText().toString();
                String strMail = addmail.getText().toString();

                RequestParams params = new RequestParams();
                params.put("name", strName);
                params.put("address", strAddress);
                params.put("phone", strPhone);
                params.put("description", strDescription);
                params.put("mail", strMail);

                Request.post("https://viabrico.herokuapp.com/suppliers/", token, params, new TextHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("response : ", responseString);
                        Intent intent = new Intent(getApplicationContext(), FournisseursActivity.class);
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
