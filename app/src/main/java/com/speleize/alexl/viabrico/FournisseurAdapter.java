package com.speleize.alexl.viabrico;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.LENGTH_LONG;

public class FournisseurAdapter extends RecyclerView.Adapter<FournisseurViewHolder> {
    private List<Fournisseur> listeFournisseur = null;
    private SharedPreferences getToken;
    public String token;
    private final FournisseursActivity fournisseursActivity;

    /**
     * Constructeur.
     * @param listeFournisseur Liste de fournisseurs
     */

    public FournisseurAdapter(List<Fournisseur> listeFournisseur, FournisseursActivity fournisseursActivity, String token){
        this.listeFournisseur = listeFournisseur;
        this.token = token;
        this.fournisseursActivity = fournisseursActivity;



    }

    @Override
    public FournisseurViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View viewFournisseur = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fournisseur, parent, false);
        FournisseurViewHolder fournisseurViewHolder = new FournisseurViewHolder(viewFournisseur);
        return new FournisseurViewHolder(viewFournisseur);
    }

    public class activity extends Activity {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }
    }

    @Override
    public void onBindViewHolder(FournisseurViewHolder holder, int position)
    {
        delete(getItemParPosition(position), holder);
        edit(getItemParPosition(position), holder);
        onClick(getItemParPosition(position), holder);
        holder.textViewName.setText(listeFournisseur.get(position).getName());
        holder.textViewDescription.setText(listeFournisseur.get(position).getDescription());
        holder.textViewAddress.setText(listeFournisseur.get(position).getAddress());
        holder.textViewNumber.setText(listeFournisseur.get(position).getPhone().toString());
        holder.textViewMail.setText(listeFournisseur.get(position).getMail());

    }

    @Override
    public int getItemCount(){
        return listeFournisseur.size();
    }

    /**
     * Retourne la ressource à la position voulue.
     *
     * @param position Position
     * @return Ressources
     */
    public Fournisseur getItemParPosition(int position) {
        return listeFournisseur.get(position);
    }



    public void onClick(final Fournisseur fournisseur, final FournisseurViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), SelectedFournisseurActivity.class);
                Log.d("ID du fournisseur : ", fournisseur.getId().toString());
                intent.putExtra("idFournisseur", fournisseur.getId());


                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    public void edit(final Fournisseur fournisseur, final FournisseurViewHolder holder) {
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.editButton.getContext(), EditFournisseurActivity.class);
                intent.putExtra("idFournisseur", fournisseur.getId());
                Log.d("id :", fournisseur.getId().toString());
                holder.editButton.getContext().startActivity(intent);

            }
        });
    }
    public void delete(final Fournisseur fournisseur, final FournisseurViewHolder holder) {
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupération de l'ID :

                Integer id = fournisseur.getId();

                Request.delete("https://viabrico.herokuapp.com/suppliers/" + id , token,null, new TextHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("Succes : ", responseString);

                        Toast.makeText(holder.deleteButton.getContext(),fournisseur.getName() + " supprimé !",LENGTH_LONG);
                        Intent intent = new Intent(holder.deleteButton.getContext(), FournisseursActivity.class);
                        holder.deleteButton.getContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("error :", responseString);

                    }

                });

            }
        });
    }

    /**
     * Ajout d'un fournisseur à la liste.
     * @param fournisseur Fournisseur
     */

    public void ajouterFournisseur(Fournisseur fournisseur)
    {
        listeFournisseur.add(0, fournisseur);
        notifyItemInserted(0);
    }
}


