package com.speleize.alexl.viabrico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

    }

    public void goToFournisseur(View view){

        Intent intent = new Intent(this, FournisseursActivity.class);
        startActivity(intent);
    }

    public void goToCreate(View view){

        Intent intent = new Intent(this, CreateFournisseurActivity.class);
        startActivity(intent);
    }
}
