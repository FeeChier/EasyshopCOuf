package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auth.Model.User;
import com.example.auth.Premium.PremiumActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private String userId;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private DocumentReference mUserRef;
    private TextView fnamev,lnamev, emailv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userId = mAuth.getCurrentUser().getUid();
        mUserRef = fstore.collection("Users").document(userId);
        fnamev = findViewById(R.id.prenomprofilnrml);
        lnamev = findViewById(R.id.nomprofilnrml);
        emailv = findViewById(R.id.emailprofilnormal);
        Button sedeco = findViewById(R.id.seDeconnecternrml);
        sedeco.setOnClickListener(this);
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
                startActivity(new Intent(ProfileActivity.this, PremiumActivity.class));
                return true;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                return true;
            case R.id.nav_maliste:
                startActivity(new Intent(ProfileActivity.this, ListeActivity.class));
                return true;
            case R.id.nav_moncompte:
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seDeconnecternrml:
                seDeconnecter();
                break;
    }
}

    private void seDeconnecter() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(ProfileActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    String fname = user.getFirstName();
                    String lname= user.getLastName();
                    String email = user.getEmail();
                    fnamev.setText(fname);
                    lnamev.setText(lname);
                    emailv.setText(email);

                }
            }
        });
}}