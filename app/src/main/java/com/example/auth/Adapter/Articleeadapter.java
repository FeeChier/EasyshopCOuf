package com.example.auth.Adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auth.Model.Article;
import com.example.auth.ArticleActivity;
import com.example.auth.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private static final String KEY_NOM = "nom";
        private static final String KEY_DESCRIPTION = "description";
        private static final String KEY_PRIX = "prix";
        TextView nameView;
        TextView descriptionView;
        ImageView article_image;
        TextView prix;
        FloatingActionButton delete;
        private ArticleActivity articleActivity = new ArticleActivity();


        public String Id (){
            String a = articleActivity.getArticleId();
            return a;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.article_magasin_name);
            descriptionView = itemView.findViewById(R.id.article_magasin_description);
            prix = itemView.findViewById(R.id.article_magasin_prix);
            article_image = itemView.findViewById(R.id.image_article);
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

