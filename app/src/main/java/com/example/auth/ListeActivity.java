package com.example.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListeActivity extends AppCompatActivity implements ListAdapter.OnListItemClick {

    private RecyclerView mfirestorelist;
    private FirestorePagingAdapter adapter;
    private FirebaseFirestore mFirestore;
    String UserId;
    private FirebaseAuth mAuth;
    ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpRecyclerView();
        mfirestorelist = findViewById(R.id.liste_articles_added);

    }

    private void setUpRecyclerView() {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        Query query = mFirestore.collection("Users").document(UserId).collection("Articles");
        //FirestoreRecyclerOptions<ListModel> options = new FirestoreRecyclerOptions.Builder<ListModel>().setQuery(query, ListModel.class).build();

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(5)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<ListModel> options = new FirestorePagingOptions.Builder<ListModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<ListModel>() {
                    @NonNull
                    @Override
                    public ListModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        ListModel listModel = snapshot.toObject(ListModel.class);
                        String itemId = snapshot.getId();
                        listModel.setArticle_id(itemId);
                        return listModel;
                    }
                })
                .build();

        adapter = new ListAdapter(options,this);

        RecyclerView recyclerView = findViewById(R.id.liste_articles_added);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent intent= new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.KEY_ARTICLE_ID, snapshot.getId());
        startActivity(new Intent(this,ArticleActivity.class));
    }
}