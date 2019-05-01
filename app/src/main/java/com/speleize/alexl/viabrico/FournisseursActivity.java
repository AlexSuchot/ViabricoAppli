package com.speleize.alexl.viabrico;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
                Type listType = new TypeToken<List<Fournisseur>>(){}.getType();
                List<Fournisseur> listeFournisseur = gson.fromJson(responseString, listType);
                Log.d("response string :", responseString);
                fournisseurAdapter = new FournisseurAdapter(listeFournisseur, FournisseursActivity.this);
                recyclerView.setAdapter(fournisseurAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error :", responseString);

            }

        });
    }
}
