package com.speleize.alexl.viabrico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

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

        //vues
        recyclerView = findViewById(R.id.liste_fournisseur);


        //Pour de meilleurs performances
        recyclerView.setHasFixedSize(true);

        //Layout manager, décrivant comment les items sont mis
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //contenu d'exemple
        List<Fournisseur> listeFournisseur = new ArrayList<>();
        for (int a = 0; a < 10 ;a++)
        {
            listeFournisseur.add(new Fournisseur());
        }

        //adapter
        fournisseurAdapter = new FournisseurAdapter(listeFournisseur);
        recyclerView.setAdapter(fournisseurAdapter);

    }
}
