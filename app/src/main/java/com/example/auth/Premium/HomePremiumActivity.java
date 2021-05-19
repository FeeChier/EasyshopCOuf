package com.example.auth.Premium;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auth.Adapter.Articleeadapter;
import com.example.auth.Adapter.CategoryAdapter;
import com.example.auth.Adapter.DiscountedProductAdapter;
import com.example.auth.Adapter.FirestoreAdapter;
import com.example.auth.Adapter.PremiumHomeAdapter;
import com.example.auth.AllCategory;
import com.example.auth.ListeActivity;
import com.example.auth.MagasinActivity;
import com.example.auth.Model.Category;
import com.example.auth.Model.DiscountedProducts;
import com.example.auth.Model.MagasinPremiumModel;
import com.example.auth.Model.ModelMagasin;
import com.example.auth.OptionPremiumCategorie;
import com.example.auth.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.auth.R.drawable.discountberry;
import static com.example.auth.R.drawable.discountbrocoli;
import static com.example.auth.R.drawable.discountmeat;

public class HomePremiumActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener, FirestoreAdapter.OnListItemClick {

    RecyclerView discountRecyclerView, categoryRecyclerView;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Magasins");
    private FirestoreAdapter adapter;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;


    private ViewGroup mEmptyView;
    TextView allCategory;
    ImageView cart;
    ImageView option;
    private Articleeadapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_home);

        mEmptyView = findViewById(R.id.view_empty_article);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence>adaptera = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptera);
        spinner.setOnItemSelectedListener(this);

        discountRecyclerView = findViewById(R.id.discountedRecycler);

        cart = findViewById(R.id.cartpremium);
        option = findViewById(R.id.optionpremium);


        allCategory.setOnClickListener(this);
        cart.setOnClickListener(this);
        option.setOnClickListener(this);
        // Ajout data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, R.drawable.vegetable));
        discountedProductsList.add(new DiscountedProducts(2, R.drawable.meat));
        discountedProductsList.add(new DiscountedProducts(3, R.drawable.cookies));
        discountedProductsList.add(new DiscountedProducts(4, R.drawable.fruits));

        // Ajout data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.fruits));
        categoryList.add(new Category(2, R.drawable.cookies));
        categoryList.add(new Category(3, R.drawable.meat));
        categoryList.add(new Category(4, R.drawable.vegetable));

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        Query query =collectionReference;

        RecyclerView mfirestorelist = findViewById(R.id.premiummagasin);
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

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cartpremium:
                Intent p = new Intent(HomePremiumActivity.this, ListeActivity.class);
                startActivity(p);
                break;
            case R.id.optionpremium:
                Intent o = new Intent(HomePremiumActivity.this, OptionPremiumCategorie.class);
                startActivity(o);
                break;

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Pour " + text + " personnes ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("ITEM_CLICK", "Clicked an item : " + position + " and the ID is :" + snapshot.getId());
        Intent intent= new Intent(this, MagasinActivity.class);
        intent.putExtra(MagasinActivity.KEY_MAGASINS_ID, snapshot.getId());
        startActivity(intent);
    }
}
