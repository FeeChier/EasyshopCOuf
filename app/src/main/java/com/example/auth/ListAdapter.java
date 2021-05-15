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
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class ListAdapter extends FirestorePagingAdapter<ListModel, ListAdapter.ListHolder> {

    private OnListItemClick onListItemClick;

    public ListAdapter(@NonNull @NotNull FirestorePagingOptions<ListModel> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ListAdapter.ListHolder holder, int position, @NonNull @NotNull ListModel model) {
        holder.list_nom.setText(model.getNom());
        holder.list_description.setText(model.getDescription());
        holder.list_prix.setText(model.getPrix() + " €");
        holder.list_qtt.setText(model.getQtt());
        holder.list_total.setText(model.getTotal());
    }

    @NonNull
    @NotNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liste_article,parent,false);
        return new ListHolder(v);
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView list_nom;
        TextView list_description;
        TextView list_prix;
        TextView list_qtt;
        TextView list_total;
        public ListHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            list_nom = itemView.findViewById(R.id.list_name);
            list_description = itemView.findViewById(R.id.list_description);
            list_prix = itemView.findViewById(R.id.list_prix);
            list_qtt = itemView.findViewById(R.id.list_qtt);
            list_total = itemView.findViewById(R.id.total_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }
}
