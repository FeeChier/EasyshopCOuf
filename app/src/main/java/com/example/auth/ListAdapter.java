package com.example.auth;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class ListAdapter extends FirestoreRecyclerAdapter<ListModel, ListAdapter.ListHolder> {


    public ListAdapter(@NonNull @NotNull FirestoreRecyclerOptions<ListModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ListAdapter.ListHolder holder, int position, @NonNull @NotNull ListModel model) {
        holder.list_nom.setText(model.getNom());
        holder.list_description.setText(model.getDescription());
        holder.list_prix.setText(model.getPrix());
    }

    @NonNull
    @NotNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liste_article,parent,false);
        return new ListHolder(v);
    }

    class ListHolder extends RecyclerView.ViewHolder{
        TextView list_nom;
        TextView list_description;
        TextView list_prix;
        public ListHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            list_nom = itemView.findViewById(R.id.list_name);
            list_description = itemView.findViewById(R.id.list_description);
            list_prix = itemView.findViewById(R.id.list_prix);
        }
    }
}
