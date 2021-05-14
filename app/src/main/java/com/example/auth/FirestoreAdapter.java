package com.example.auth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FirestoreAdapter extends FirestorePagingAdapter<ModelMagasin, FirestoreAdapter.MagasinViewHolder> {

    private OnListItemClick onListItemClick;


    public FirestoreAdapter(@NonNull FirestorePagingOptions<ModelMagasin> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull MagasinViewHolder holder, int position, @NonNull ModelMagasin model) {
        holder.list_nom.setText(model.getNom());
        holder.list_adresse.setText(model.getAdresse());
        Picasso.get().load(model.getPhoto()).into(holder.magasin_logo);
    }



    @NonNull
    @Override
    public MagasinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new MagasinViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {
            case LOADING_INITIAL:
                Log.d("PAGING_LOG", "Loading Initial Data");
                break;
            case LOADING_MORE:
                Log.d("PAGING_LOG", "Loading Next Page");
                break;
            case FINISHED:
                Log.d("PAGING_LOG", "All Data Loaded");
            case ERROR:
                Log.d("PAGING_LOG", "Error Loading Data");
                break;
            case LOADED:
                Log.d("PAGING_LOG", "Total Items Loaded : " + getItemCount());
                break;
        }
    }

    public class MagasinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView list_nom;
        private TextView list_adresse;
        private ImageView magasin_logo;

        public MagasinViewHolder(@NonNull View itemView) {
            super(itemView);

            list_nom = itemView.findViewById(R.id.list_name);
            list_adresse = itemView.findViewById(R.id.list_adresse);
            magasin_logo = itemView.findViewById(R.id.magasin_logo);
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
