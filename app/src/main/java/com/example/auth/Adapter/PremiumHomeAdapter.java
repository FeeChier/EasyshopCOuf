package com.example.auth.Adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auth.ArticleActivity;
import com.example.auth.Model.Article;
import com.example.auth.Model.ModelMagasin;
import com.example.auth.Premium.HomePremiumActivity;
import com.example.auth.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public abstract class PremiumHomeAdapter extends FirestoreeAdapter<PremiumHomeAdapter.ViewHolder> {


public interface OnPremiumSelectedListener {
    void onPremiumSelected(DocumentSnapshot magasin);
}

    private OnPremiumSelectedListener mListener;

    public PremiumHomeAdapter(Query query, OnPremiumSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_premium_magasin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }


class ViewHolder extends RecyclerView.ViewHolder {

    TextView nameView;
    TextView adresseView;
    ImageView magasin_image;



    public ViewHolder(View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.list_namepremium);
        adresseView = itemView.findViewById(R.id.list_adressepremium);
        magasin_image = itemView.findViewById(R.id.magasin_logopremium);


    }

    public void bind(final DocumentSnapshot snapshot,
                     final OnPremiumSelectedListener listener) {
        ModelMagasin modelMagasin = snapshot.toObject(ModelMagasin.class);
        nameView.setText(modelMagasin.getNom());
        adresseView.setText(modelMagasin.getAdresse());
        Picasso.get().load(modelMagasin.getPhoto()).into(magasin_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPremiumSelected(snapshot);
                }
            }
        });
    }
}

}