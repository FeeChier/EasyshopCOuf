package com.example.auth;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ArticleDetail";
    public static final String KEY_ARTICLE_ID = "key_article_id";

    public static final String KEY_MAGASIN_ID = "key_magasin_id";

    private static final String KEY_NOM = "nom";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRIX = "prix";


    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private DocumentReference mArticleRef;
    private ListenerRegistration mArticleRegistration;
    private TextView mNameView;
    private ImageView mlogo;
    private TextView mDescription;
    private TextView mPrix;
    private Articleeadapter mArticleAdapter;
    private TextView mDescriptionComplete;
    String UserId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
        findViewById(R.id.article_add).setOnClickListener(this);
        String articleId = getIntent().getExtras().getString(KEY_ARTICLE_ID);
        String magasinIdd = MagasinActivity.getMagasinId();
        System.out.println(articleId + " " + magasinIdd);

        mArticleRef = mFirestore.collection("Magasins").document(magasinIdd).collection("Articles").document(articleId);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mArticleRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(ArticleActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }
                if (documentSnapshot.exists()) {
                    Article article = documentSnapshot.toObject(Article.class);
                    String name = article.getName();
                    String description = article.getDescription();
                    String prix = article.getPrice();
                    String description_complete = article.getDescription_complete();
                    mNameView.setText(""+ name );
                    mDescription.setText(""+ description);
                    mPrix.setText(""+ prix);
                    mDescriptionComplete.setText(" "+description_complete);
                    Picasso.get().load(article.getPhoto()).into(mlogo);
                } else {
                    mNameView.setText("");
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_button_back:
                onBackArrowClicked(v);
                break;
            case R.id.article_add:
                onAddArticleClicked(v);
                break;
        }
    }

    public void onBackArrowClicked(View view) {
        onBackPressed();
    }
    public void onAddArticleClicked (View view){
        String name = mNameView.getText().toString();
        String description = mDescription.getText().toString();
        String price = mPrix.getText().toString();

        Map<String, Object> articles = new HashMap<>();
        articles.put(KEY_NOM, name);
        articles.put(KEY_DESCRIPTION, description);
        articles.put(KEY_PRIX, price);
        UserId = mAuth.getCurrentUser().getUid();
        mFirestore.collection("Users").document(UserId).collection("Articles").document().set(articles).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ArticleActivity.this,"L'article a été ajouté", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ArticleActivity.this,"Erreur !", Toast.LENGTH_LONG).show();
                Log.d(TAG, e.toString());
            }
        });
    }

}