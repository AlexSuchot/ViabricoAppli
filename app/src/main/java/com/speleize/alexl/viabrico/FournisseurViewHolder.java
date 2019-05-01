package com.speleize.alexl.viabrico;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class FournisseurViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName = null;
    TextView textViewDescription = null;
    TextView textViewAddress = null;
    TextView textViewNumber = null;
    TextView textViewMail = null;

    /**
     * Constructeur.
     *
     * @param itemView Vue item
     */
    FournisseurViewHolder(final View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.name);
        textViewMail = itemView.findViewById(R.id.email);
        textViewDescription = itemView.findViewById(R.id.description);
        textViewNumber = itemView.findViewById(R.id.number);
        textViewAddress = itemView.findViewById(R.id.address);


        // listener :
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // récupération du context depuis une vue :
                Context context = itemView.getContext();
            }
        });
    }
}
