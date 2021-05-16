package com.example.auth.Premium;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auth.Adapter.CategoryAdapter;
import com.example.auth.Adapter.DiscountedProductAdapter;
import com.example.auth.Adapter.RecentlyViewedAdapter;
import com.example.auth.AllCategory;
import com.example.auth.Model.Category;
import com.example.auth.Model.DiscountedProducts;
import com.example.auth.Model.RecentlyViewed;
import com.example.auth.OptionPremiumCategorie;
import com.example.auth.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.auth.R.drawable.*;
import static com.example.auth.R.drawable.b1;
import static com.example.auth.R.drawable.b2;
import static com.example.auth.R.drawable.b3;
import static com.example.auth.R.drawable.b4;
import static com.example.auth.R.drawable.card1;
import static com.example.auth.R.drawable.card2;
import static com.example.auth.R.drawable.card3;
import static com.example.auth.R.drawable.card4;
import static com.example.auth.R.drawable.discountberry;
import static com.example.auth.R.drawable.discountbrocoli;
import static com.example.auth.R.drawable.discountmeat;
import static com.example.auth.R.drawable.ic_home_fish;
import static com.example.auth.R.drawable.ic_home_fruits;
import static com.example.auth.R.drawable.ic_home_meats;
import static com.example.auth.R.drawable.ic_home_veggies;

public class HomePremiumActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;

    TextView allCategory;
    ImageView cart;
    ImageView option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_home);

        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);

        cart = findViewById(R.id.cartpremium);
        option = findViewById(R.id.optionpremium);



        allCategory.setOnClickListener(this);
        cart.setOnClickListener(this);
        option.setOnClickListener(this);

        // adding data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, discountberry));
        discountedProductsList.add(new DiscountedProducts(2, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, discountmeat));
        discountedProductsList.add(new DiscountedProducts(4, discountberry));
        discountedProductsList.add(new DiscountedProducts(5, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(6, discountmeat));

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.fruits));
        categoryList.add(new Category(2, R.drawable.cookies));
        categoryList.add(new Category(3, R.drawable.meat));
        categoryList.add(new Category(4, R.drawable.vegetable));

        // adding data to model
       recentlyViewedList = new ArrayList<>();
       recentlyViewedList.add(new RecentlyViewed("Watermelon", "Watermelon has high water content and also provides some fiber.", "₹ 80", "1", "KG", card4, b4));
       recentlyViewedList.add(new RecentlyViewed("Papaya", "Papayas are spherical or pear-shaped fruits that can be as long as 20 inches.", "₹ 85", "1", "KG", card3, b3));
       recentlyViewedList.add(new RecentlyViewed("Strawberry", "The strawberry is a highly nutritious fruit, loaded with vitamin C.", "₹ 30", "1", "KG", card2, b1));
       recentlyViewedList.add(new RecentlyViewed("Kiwi", "Full of nutrients like vitamin C, vitamin K, vitamin E, folate, and potassium.", "₹ 30", "1", "PC", card1, b2));

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
       setRecentlyViewedRecycler(recentlyViewedList);

    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this,recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
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
    //Now again we need to create a adapter and model class for recently viewed items.
    // lets do it fast.
}
