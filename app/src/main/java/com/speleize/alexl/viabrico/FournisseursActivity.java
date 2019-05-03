package com.speleize.alexl.viabrico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FournisseursActivity extends AppCompatActivity {


    //Vues
    private RecyclerView recyclerView = null;


    //Adapter
    private FournisseurAdapter fournisseurAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseurs);


        // Récupération du token :

        SharedPreferences getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
        String token = getToken.getString("token", "");

        Log.d("token retrieve :", token);

        //vues
        recyclerView = findViewById(R.id.liste_fournisseur);


        //Pour de meilleurs performances
        recyclerView.setHasFixedSize(true);

        //Layout manager, décrivant comment les items sont mis
        LinearLayoutManager layoutManager = new LinearLayoutManager(FournisseursActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //contenu d'exemple
        List<Fournisseur> listeFournisseur = new ArrayList<>();

        Gson gson = new Gson();

        Request.get("https://viabrico.herokuapp.com/suppliers", token, null, new TextHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // On récupère tous les fournisseurs :
                Type listType = new TypeToken<List<Fournisseur>>(){}.getType();
                List<Fournisseur> listeFournisseur = gson.fromJson(responseString, listType);
                Log.d("response string :", responseString);
                // On ajoute la liste des fournisseurs dans le recycler view :
                fournisseurAdapter = new FournisseurAdapter(listeFournisseur, FournisseursActivity.this, token);
                recyclerView.setAdapter(fournisseurAdapter);
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
