package com.example.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

public class ArticleActivity extends AppCompatActivity implements EventListener<DocumentSnapshot> ,
        View.OnClickListener{

    private static final String TAG = "ArticleDetail";
    public static final String KEY_ARTICLE_ID = "key_article_id";

    public static final String KEY_MAGASIN_ID = "key_magasin_id";

    private FirebaseFirestore mFirestore;
    private DocumentReference mArticleRef;
    private ListenerRegistration mArticleRegistration;
    private TextView mNameView;
    private ImageView mlogo;
    private TextView mDescription;
    private TextView mPrix;
    private Articleeadapter mArticleAdapter;
    private TextView mDescriptionComplete;

    private RecyclerView mArticleRecycler;
    private ViewGroup mEmptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mNameView = findViewById(R.id.article_name);
        mlogo = findViewById(R.id.article_image);
        mDescription = findViewById(R.id.article_description);
        mPrix = findViewById(R.id.article_prix);
        mDescriptionComplete = findViewById(R.id.description_complete);
        findViewById(R.id.article_button_back).setOnClickListener(this);
        String articleId = getIntent().getExtras().getString(KEY_ARTICLE_ID);
        String magasinIdd = MagasinActivity.getMagasinId();
        System.out.println(articleId+" "+magasinIdd);
        mFirestore = FirebaseFirestore.getInstance();

        mArticleRef = mFirestore.collection("Magasins").document(magasinIdd);

        Query articleQuery = mArticleRef.collection("Articles");


    }
    @Override
    public void onStart() {
        super.onStart();
        mArticleRegistration = mArticleRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();


        if (mArticleRegistration != null) {
            mArticleRegistration.remove();
            mArticleRegistration = null;
        }
    }
    @Override
    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "magasin:onEvent", e);
            return;
        }

        onArticleLoaded(snapshot.toObject(Article.class));
    }

    private void onArticleLoaded(Article article) {
        mNameView.setText(article.getName());
        mDescription.setText(article.getDescription());
        mPrix.setText(article.getPrice());

        // Background image
        Glide.with(mlogo.getContext())
                .load(article.getPhoto())
                .into(mlogo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_button_back:
                onBackArrowClicked(v);
                break;

        }
    }

    public void onBackArrowClicked(View view) {
        onBackPressed();
    }
}