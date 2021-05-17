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
import com.example.auth.Model.MagasinPremiumModel;
import com.example.auth.Model.ModelMagasin;
import com.example.auth.Premium.HomePremiumActivity;
import com.example.auth.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class PremiumHomeAdapter extends FirestoreRecyclerAdapter<MagasinPremiumModel,PremiumHomeAdapter.magasinpholder> {

    public PremiumHomeAdapter(@NonNull FirestoreRecyclerOptions<MagasinPremiumModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull magasinpholder holder, int position, @NonNull MagasinPremiumModel model) {
        holder.magasinnom.setText(model.getNom());
        holder.magasinadresse.setText(model.getAdresse());
    }

    @NonNull
    @Override
    public magasinpholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_premium_magasin, parent, false);
        return new magasinpholder(view);
    }

    class magasinpholder extends RecyclerView.ViewHolder{

    TextView magasinnom;
    TextView magasinadresse;
        public magasinpholder(@NonNull View itemView) {
            super(itemView);
            magasinnom = itemView.findViewById(R.id.list_namepremium);
            magasinadresse = itemView.findViewById(R.id.list_adressepremium);
        }
    }
}