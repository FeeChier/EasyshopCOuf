package com.example.auth.Premium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.auth.HomeActivity;
import com.example.auth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Paiement extends AppCompatActivity implements View.OnClickListener {
    public EditText CB;
    public EditText Date;
    public EditText CVV;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    public Button Payer;
    public static final String TAG = "PaiementDetail";
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        CB = (EditText) findViewById(R.id.paiement_CB);
        Date = (EditText) findViewById(R.id.paiement_date_expi);
        CVV = (EditText) findViewById(R.id.paiement_CVV);

        findViewById(R.id.paiement_confirmer_Button).setOnClickListener(this);
        findViewById(R.id.paiement_plus_tard).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        ImageButton logoButton = findViewById(R.id.logoButton);
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.logoButton){
                    startActivity(new Intent(Paiement.this, HomeActivity.class));
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paiement_confirmer_Button:
                String cb = CB.getText().toString().trim();
                String date = Date.getText().toString().trim();
                String cvv = CVV.getText().toString().trim();

                if (cb.length() != 16){
                    CB.setError("Veuillez entrer une Carte Bancaire Valide");
                    CB.requestFocus();
                    return;
                }

                if (date.isEmpty()) {
                    Date.setError("Veuillez entrer une date");
                    Date.requestFocus();
                    return;
                }

                if (cvv.isEmpty()) {
                    CVV.setError("Veuillez entrer un CVV");
                    CVV.requestFocus();
                    return;
                }

                if (cvv.length() != 3) {
                    CVV.setError("Veuillez entrer un CVV Valide");
                    CVV.requestFocus();
                    return;
                }

                userId = mAuth.getCurrentUser().getUid();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                DocumentReference documentReference = fstore.collection("Users").document(userId);

                documentReference.update("premiumUser",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess : user profile is now premium" + userId);

                        startActivity(new Intent(Paiement.this, HomePremiumActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });
                break;
            case R.id.paiement_plus_tard:
                startActivity(new Intent(Paiement.this,HomeActivity.class));
                break;
        }

    }
}