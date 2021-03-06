package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auth.Premium.HomePremiumActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    public static final String TAG = "ConnexionDetail";
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.connexionButton);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.paiement_date_expi);
        editTextPassword = (EditText) findViewById(R.id.password_connexion_text);

        progressBar = (ProgressBar) findViewById(R.id.connexion_progressbar);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.connexionButton:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Veuillez entrer un Email");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Veuillez entrer un Email Valide");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Veuillez entrer un mot de passe");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Le mot de passe est compos?? de plus de 6 caract??res");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {

                        mAuth = FirebaseAuth.getInstance();
                        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                        userId = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("Users").document(userId);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Boolean PremiumUser = (Boolean) document.getBoolean("premiumUser");
                                        if (PremiumUser == false){
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                        }else{

                                            progressBar.setVisibility(View.GONE);
                                            startActivity(new Intent(MainActivity.this, HomePremiumActivity.class));
                                        }
                                    } else {

                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "V??rifiez votre email pour valider votre connexion", Toast.LENGTH_LONG).show();

                        progressBar.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Erreur ! Veuillez v??rifier vos identifiants", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
