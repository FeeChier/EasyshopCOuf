package com.example.auth.Premium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.auth.HomeActivity;
import com.example.auth.ListeActivity;
import com.example.auth.MainActivity;
import com.example.auth.OptionActivity;
import com.example.auth.R;
import com.google.firebase.auth.FirebaseAuth;

public class PremiumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton logoButton = findViewById(R.id.logoButton);
        Button essai = findViewById(R.id.paiement);
        essai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PremiumActivity.this, Paiement.class));
            }
        });
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.logoButton){
                    startActivity(new Intent(PremiumActivity.this, HomeActivity.class));
                }

            }
        });

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
                startActivity(new Intent(PremiumActivity.this, PremiumActivity.class));
                return true;
            case R.id.nav_options:
                startActivity(new Intent(PremiumActivity.this, OptionActivity.class));
                return true;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PremiumActivity.this, MainActivity.class));
                return true;
            case R.id.nav_maliste:
                startActivity(new Intent(PremiumActivity.this, ListeActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}