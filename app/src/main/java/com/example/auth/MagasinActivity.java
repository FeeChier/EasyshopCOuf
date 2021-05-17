package com.example.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.auth.Adapter.Articleeadapter;
import com.example.auth.Model.ModelMagasin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

public class MagasinActivity extends AppCompatActivity implements
        View.OnClickListener, EventListener<DocumentSnapshot>, Articleeadapter.OnArticleSelectedListener {

    private static final String TAG = "MagasinDetail";

    public static final String KEY_MAGASINS_ID = "key_magasins_id";
    private static String magasinId;

    private ImageView mlogo;
    private TextView mNameView;
    private TextView mAdressView;
    private RecyclerView mArticleRecycler;
    private ViewGroup mEmptyView;
    private TextView mCategoryView;
    private FloatingActionButton add;
    private FloatingActionButton delete;

    private FirebaseFirestore mFirestore;
    private DocumentReference mMagasinRef;
    private ListenerRegistration mMagasinRegistration;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Articleeadapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magasin);

        mlogo = findViewById(R.id.magasin_image);
        mNameView = findViewById(R.id.magasin_name);
        mAdressView = findViewById(R.id.magasin_adress);
        mArticleRecycler = findViewById(R.id.recycler_article);
        mEmptyView = findViewById(R.id.view_empty_article);
        mCategoryView = findViewById(R.id.magasin_category);
        delete = findViewById(R.id.delete_article);
        findViewById(R.id.magasin_button_back).setOnClickListener(this);

        magasinId = getIntent().getExtras().getString(KEY_MAGASINS_ID);
        mFirestore = FirebaseFirestore.getInstance();

        mMagasinRef = mFirestore.collection("Magasins").document(magasinId);
        Query articleQuery = mMagasinRef.collection("Articles");

        mArticleAdapter = new Articleeadapter(articleQuery, this) {
            @Override
            protected void onDataChanged(){
                if (getItemCount() == 0) {
                    mArticleRecycler.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }else {
                    mArticleRecycler.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        };

        mArticleRecycler.setLayoutManager(new LinearLayoutManager(this));
        mArticleRecycler.setAdapter(mArticleAdapter);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.magasin_button_back:
                onBackArrowClicked(v);
                break;

        }
    }

    private void onBackArrowClicked(View v) {onBackPressed();
    }


    @Override
    public void onStart() {
        super.onStart();

        mArticleAdapter.startListening();
        mMagasinRegistration = mMagasinRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        mArticleAdapter.stopListening();

        if (mMagasinRegistration != null) {
            mMagasinRegistration.remove();
            mMagasinRegistration = null;
        }
    }

    @Override
    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "magasin:onEvent", e);
            return;
        }

        onMagasinLoaded(snapshot.toObject(ModelMagasin.class));
    }
    private void onMagasinLoaded(ModelMagasin magasin) {
        mNameView.setText(magasin.getNom());
        mAdressView.setText(magasin.getAdresse());
        mCategoryView.setText(magasin.getCategorie());

        // Background image
        Glide.with(mlogo.getContext())
                .load(magasin.getPhoto())
                .into(mlogo);
    }

    @Override
    public void onArticleSelected(DocumentSnapshot article) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.KEY_ARTICLE_ID, article.getId());
        startActivity(intent);
    }
    public static String getMagasinId(){
        return magasinId;
    }
}