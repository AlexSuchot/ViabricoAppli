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

public class EditFournisseurActivity extends AppCompatActivity {

    private EditText editname;
    private EditText editaddress;
    private EditText editphone;
    private EditText editdescription;
    private EditText editmail;
    private Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fournisseur);

        editname = findViewById(R.id.editname);
        editaddress = findViewById(R.id.editaddress);
        editphone = findViewById(R.id.editphone);
        editdescription = findViewById(R.id.editdescription);
        editmail = findViewById(R.id.editmail);
        updateButton = findViewById(R.id.submit);


        // Récupération du token :

        SharedPreferences getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
        String token = getToken.getString("token", "");


        // Récupération de l'ID :

        Intent getIntent = getIntent();
        Integer id = getIntent.getIntExtra("idFournisseur",0);


        Request.get("https://viabrico.herokuapp.com/suppliers/" + id , token,null,new TextHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("response : ", responseString);
                Gson gson = new Gson();
                Fournisseur json = gson.fromJson(responseString, Fournisseur.class);

                // Données par défaut :
                editname.setText(json.getName());
                editdescription.setText(json.getDescription());
                editaddress.setText(json.getAddress());
                editmail.setText(json.getMail());
                editphone.setText(json.getPhone().toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error :", responseString);

            }

        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values to string :
                String strName = editname.getText().toString();
                String strAddress = editaddress.getText().toString();
                String strPhone = editphone.getText().toString();
                String strDescription = editdescription.getText().toString();
                String strMail = editmail.getText().toString();

                RequestParams params = new RequestParams();
                params.put("name", strName);
                params.put("address", strAddress);
                params.put("phone", strPhone);
                params.put("description", strDescription);
                params.put("mail", strMail);

                Request.put("https://viabrico.herokuapp.com/suppliers/"+ id, token, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseBody) {

                        // called when response HTTP status is "200 OK"
                        Log.d("res", "code: " + statusCode);
                        Log.d("res", "headers: " + headers.toString());
                        Log.d("res", "res:" + responseBody);

                        Intent intent = new Intent(getApplicationContext(), FournisseursActivity.class);
                        startActivity(intent);
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
