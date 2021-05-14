package com.example.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class HomeActivity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick{
    private FirebaseFirestore firebaseFirestore;


    String UserId;
    private RecyclerView mfirestorelist;
    private FirestorePagingAdapter adapter;
    private ImageButton btt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mfirestorelist = findViewById(R.id.liste_firestore);
        firebaseFirestore = FirebaseFirestore.getInstance();


        ImageButton logoButton = findViewById(R.id.logoButton);


        Query query = firebaseFirestore.collection("Magasins");


        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(5)
                .setPageSize(3)
                .build();

        FirestorePagingOptions<ModelMagasin> options = new FirestorePagingOptions.Builder<ModelMagasin>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<ModelMagasin>() {
                    @NonNull
                    @Override
                    public ModelMagasin parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        ModelMagasin modelMagasin = snapshot.toObject(ModelMagasin.class);
                        String itemId = snapshot.getId();
                        modelMagasin.setItem_id(itemId);
                        return modelMagasin;
                    }
                })
                .build();

        adapter = new FirestoreAdapter(options, this);


        mfirestorelist.setHasFixedSize(true);
        mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mfirestorelist.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_premium:
                startActivity(new Intent(HomeActivity.this, PremiumActivity.class));
                return true;
            case R.id.nav_options:
                startActivity(new Intent(HomeActivity.this, OptionActivity.class));
                return true;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                return true;
            case R.id.nav_maliste:
                startActivity(new Intent(HomeActivity.this, ListeActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("ITEM_CLICK", "Clicked an item : " + position + " and the ID is :" + snapshot.getId());
        Intent intent= new Intent(this, MagasinActivity.class);
        intent.putExtra(MagasinActivity.KEY_MAGASINS_ID, snapshot.getId());
        startActivity(intent);


    }



}