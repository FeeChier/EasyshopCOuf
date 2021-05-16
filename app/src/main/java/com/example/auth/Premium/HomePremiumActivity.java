package com.example.auth.Premium;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auth.Adapter.CategoryAdapter;
import com.example.auth.Adapter.DiscountedProductAdapter;
import com.example.auth.AllCategory;
import com.example.auth.Model.Category;
import com.example.auth.Model.DiscountedProducts;
import com.example.auth.Model.ModelMagasin;
import com.example.auth.OptionPremiumCategorie;
import com.example.auth.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.auth.R.drawable.discountberry;
import static com.example.auth.R.drawable.discountbrocoli;
import static com.example.auth.R.drawable.discountmeat;

public class HomePremiumActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView discountRecyclerView, categoryRecyclerView;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    private RecyclerView mfirestorelist;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;


    private ViewGroup mEmptyView;
    TextView allCategory;
    ImageView cart;
    ImageView option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_home);

        mEmptyView = findViewById(R.id.view_empty_article);
        mfirestorelist = findViewById(R.id.magasinproche_item);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("Magasins");

        FirestoreRecyclerOptions<ModelMagasin> options = new FirestoreRecyclerOptions.Builder<ModelMagasin>()
                .setQuery(query, ModelMagasin.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ModelMagasin, MagasinViewHolder>(options) {
            @NonNull
            @Override
            public MagasinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_premium_magasin,parent,false);
                return new MagasinViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MagasinViewHolder holder, int position, @NonNull ModelMagasin model) {
                holder.magasin_name_premium.setText(model.getNom());
                holder.magasin_adresse_premium.setText(model.getAdresse());
            }
        };
        mfirestorelist.setHasFixedSize(true);
        mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mfirestorelist.setAdapter(adapter);



        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);

        cart = findViewById(R.id.cartpremium);
        option = findViewById(R.id.optionpremium);



        allCategory.setOnClickListener(this);
        cart.setOnClickListener(this);
        option.setOnClickListener(this);

        // Ajout data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, discountberry));
        discountedProductsList.add(new DiscountedProducts(2, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, discountmeat));
        discountedProductsList.add(new DiscountedProducts(4, discountberry));
        discountedProductsList.add(new DiscountedProducts(5, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(6, discountmeat));

        // Ajout data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.fruits));
        categoryList.add(new Category(2, R.drawable.cookies));
        categoryList.add(new Category(3, R.drawable.meat));
        categoryList.add(new Category(4, R.drawable.vegetable));

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);

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
            case R.id.allCategoryImage:
                Intent i = new Intent(HomePremiumActivity.this, AllCategory.class);
                startActivity(i);
                break;
            case R.id.cartpremium:
                break;
            case R.id.optionpremium:
                Intent o = new Intent(HomePremiumActivity.this, OptionPremiumCategorie.class);
                startActivity(o);
                break;

        }
    }


    private class MagasinViewHolder extends RecyclerView.ViewHolder{
        private TextView magasin_name_premium;
        private TextView magasin_adresse_premium;
        private ImageView logo;

        public MagasinViewHolder(@NonNull View itemView) {
            super(itemView);

            magasin_adresse_premium = itemView.findViewById(R.id.list_adressepremium);
            magasin_name_premium = itemView.findViewById(R.id.list_namepremium);
            logo = itemView.findViewById(R.id.magasin_logopremium);
        }
    }
}
