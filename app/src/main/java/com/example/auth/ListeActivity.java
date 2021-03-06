package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.auth.Adapter.ListAdapter;
import com.example.auth.Model.Article;
import com.example.auth.Model.ListModel;
import com.example.auth.Model.ListViewModel;
import com.example.auth.Premium.PremiumActivity;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.protobuf.StringValue;

public class ListeActivity extends AppCompatActivity implements ListAdapter.OnListItemClick {

    private RecyclerView mfirestorelist;
    private FirestorePagingAdapter adapter;
    private FirebaseFirestore mFirestore;
    String UserId;
    private FirebaseAuth mAuth;
    ListViewModel listViewModel;
    TextView prixtotal;

    private ArticleActivity articleActivity = new ArticleActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mfirestorelist = findViewById(R.id.liste_articles_added);
        prixtotal = findViewById(R.id.prix_total);
        double ptotal = articleActivity.getPrixtotal();
        String ptotals = String.valueOf(ptotal);
        prixtotal.setText(ptotals);
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
    public double getprixtotal(){
        double pt = articleActivity.getPrixtotal();
        return pt;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_premium:
                startActivity(new Intent(ListeActivity.this, PremiumActivity.class));
                return true;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ListeActivity.this, MainActivity.class));
                return true;
            case R.id.nav_maliste:
                startActivity(new Intent(ListeActivity.this, ListeActivity.class));
                return true;

            case R.id.nav_moncompte:
                startActivity(new Intent(ListeActivity.this, ProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.startListening();}

    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Intent intent= new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.KEY_ARTICLE_ID, snapshot.getId());
        startActivity(new Intent(this,ArticleActivity.class));
    }
}