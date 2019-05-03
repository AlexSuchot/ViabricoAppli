package com.speleize.alexl.viabrico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class SelectedFournisseurActivity extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView address;
    private TextView phone;
    private TextView email;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_fournisseur_acitivy);

        // Récupération du token :

        SharedPreferences getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
        String token = getToken.getString("token", "");


        // Récupération de l'ID :

        Intent getIntent = getIntent();
        Integer id = getIntent.getIntExtra("idFournisseur",0);

        // Vues :

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        backButton = findViewById(R.id.back);

        Request.get("https://viabrico.herokuapp.com/suppliers/" + id , token,null,new TextHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("response : ", responseString);
                Gson gson = new Gson();
                Fournisseur json = gson.fromJson(responseString, Fournisseur.class);
                name.setText(json.getName());
                description.setText(json.getDescription());
                address.setText(json.getAddress());
                phone.setText(json.getPhone().toString());
                email.setText(json.getMail());

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), FournisseursActivity.class);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error :", responseString);

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
