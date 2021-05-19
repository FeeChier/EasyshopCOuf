package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auth.Model.Article;
import com.example.auth.Model.User;
import com.example.auth.Premium.HomePremiumActivity;
import com.example.auth.Premium.Paiement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class OptionPremiumCategorie extends AppCompatActivity implements View.OnClickListener{

    private String userId;

    public static final String TAG = "OptionPremiumCategorieDetail";
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    private DocumentReference mUserRef;
    private TextView fnamev,lnamev, emailv;
    private ImageView backprofil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_premium_categorie);

        userId = mAuth.getCurrentUser().getUid();
        mUserRef = fstore.collection("Users").document(userId);
        fnamev = findViewById(R.id.prenomprofil);
        lnamev = findViewById(R.id.nomprofil);
        emailv = findViewById(R.id.emailprofil);
        backprofil = findViewById(R.id.backprofil);
        backprofil.setOnClickListener(this);
        Button sedeco = findViewById(R.id.seDeconnecter);
        Button sedesabo = findViewById(R.id.seDesabonner);
        sedeco.setOnClickListener(this);
        sedesabo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seDeconnecter:
                seDeconnecter();
                break;
            case R.id.seDesabonner:
                seDesabonner();
                break;
            case R.id.backprofil:
                onBackPressed();
                break;
        }
    }

    private void seDeconnecter() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(OptionPremiumCategorie.this, MainActivity.class));
    }

    private void seDesabonner() {

        userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("Users").document(userId);

        documentReference.update("premiumUser",false).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess : user profile is now premium" + userId);

                startActivity(new Intent(OptionPremiumCategorie.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(TAG, "onFailure: "+e.toString());
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        mUserRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(OptionPremiumCategorie.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
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