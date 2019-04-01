package com.speleize.alexl.viabrico;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FournisseurAdapter extends RecyclerView.Adapter<FournisseurAdapter.FournisseurViewHolder> {

    private List<Fournisseur> listeFournisseur = null;


    /**
     * Constructeur.
     * @param listeFournisseur Liste de fournisseurs
     */

    public FournisseurAdapter(List<Fournisseur> listeFournisseur){
        this.listeFournisseur = listeFournisseur;
    }

    @Override
    public FournisseurViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View viewFournisseur =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fournisseur, parent, false);
        return new FournisseurViewHolder(viewFournisseur);
    }

    @Override
    public void onBindViewHolder(FournisseurViewHolder holder, int position)
    {
        holder.textViewName.setText(listeFournisseur.get(position).getName());
        holder.textViewDescription.setText(listeFournisseur.get(position).getDescription());
        holder.textViewAddress.setText(listeFournisseur.get(position).getAddress());
        holder.textViewNumber.setText(listeFournisseur.get(position).getNumber());
        holder.textViewMail.setText(listeFournisseur.get(position).getEmail());

    }

    @Override
    public int getItemCount(){
        return listeFournisseur.size();
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


        /**
        * ViewHolder.
        */

        class FournisseurViewHolder extends RecyclerView.ViewHolder
        {
        TextView textViewName = null;
        TextView textViewDescription = null;
        TextView textViewAddress = null;
        TextView textViewNumber = null;
        TextView textViewMail = null;



        /**
         * Constructeur.
         * @param itemView Vue item
             */
        FournisseurViewHolder(final View itemView)
        {
            super(itemView);
            textViewName = itemView.findViewById(R.id.fournisseur_name);
            textViewMail = itemView.findViewById(R.id.fournisseur_email);
            textViewDescription = itemView.findViewById(R.id.fournisseur_description);
            textViewNumber = itemView.findViewById(R.id.fournisseur_number);
            textViewAddress = itemView.findViewById(R.id.fournisseur_address)


            // listener :
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // récupération du context depuis une vue :
                    Context context = itemView.getContext();


                }
            });
        }

}


