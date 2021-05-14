package com.example.auth;

import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public abstract class Articleeadapter extends FirestoreeAdapter<Articleeadapter.ViewHolder> {
    public interface OnArticleSelectedListener {
        void onArticleSelected(DocumentSnapshot magasin);
    }

    private OnArticleSelectedListener mListener;

    public Articleeadapter(Query query, OnArticleSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    protected abstract void onDataChanged();

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView descriptionView;
        ImageView article_image;
        TextView prix;
        TextView nameViewmag;
        FloatingActionButton add;
        FloatingActionButton delete;

        private FirebaseFirestore fstore;
        private FirebaseAuth mAuth;
        String userId;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.article_magasin_name);
            descriptionView = itemView.findViewById(R.id.article_magasin_description);
            prix = itemView.findViewById(R.id.article_magasin_prix);
            article_image = itemView.findViewById(R.id.image_article);
            add = itemView.findViewById(R.id.add_article);
            delete = itemView.findViewById(R.id.delete_article);


        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnArticleSelectedListener listener) {

            Article article = snapshot.toObject(Article.class);
            Resources resources = itemView.getResources();
            nameView.setText(article.getName());
            descriptionView.setText(article.getDescription());
            prix.setText(article.getPrice());
            Picasso.get().load(article.getPhoto()).into(article_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onArticleSelected(snapshot);
                    }
                }
            });
        }
    }

}

