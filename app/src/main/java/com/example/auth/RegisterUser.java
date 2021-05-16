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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.auth.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private ImageView banner;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private Button registerUser;

    private FirebaseAuth mAuth;

    private FirebaseFirestore fstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        banner = (ImageView) findViewById(R.id.Banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.confirmButton);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email_text);
        editTextPassword = (EditText) findViewById(R.id.password_text);
        editTextFirstName = (EditText) findViewById(R.id.userfirstname_text);
        editTextLastName = (EditText) findViewById(R.id.userlastname_text);

        progressBar = (ProgressBar) findViewById(R.id.register_progressbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.confirmButton:
                registerUser();
                break;
        }

    }

    private void registerUser() {

        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("Veuillez entrer votre prénom");
            editTextFirstName.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            editTextLastName.setError("Veuillez entrer votre Nom");
            editTextLastName.requestFocus();
            return;
        }
        if (Email.isEmpty()) {
            editTextEmail.setError("Veuillez entrer votre Email");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            editTextEmail.setError("Veuillez entrer un Email Valide");
            editTextEmail.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            editTextPassword.setError("Veuillez entrer un mot de passe");
            editTextPassword.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            editTextPassword.setError("Le mot de passe est composé de plus de 6 caractères");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, Email);
                            userId = mAuth.getCurrentUser().getUid();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            DocumentReference documentReference = fstore.collection("Users").document(userId);
                            Map<String,Object> muser = new HashMap<>();
                            muser.put("nom",firstName);
                            muser.put("email",Email);
                            muser.put("prénom", lastName);
                            muser.put("premiumUser", false);

                            currentUser.sendEmailVerification();
                            documentReference.set(muser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("", "onSuccess : user profile is created for" + userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.d("TAG", "onFailure: "+e.toString());
                                }
                            });
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegisterUser.this, "L'utilisateur a été enregistré ! Vérifiez sur votre mail pour confirmer la création du compte", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);


                                    } else {
                                        Toast.makeText(RegisterUser.this, "L'enregistrement a échoué ! Veuillez réessayer", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUser.this, "L'enregistrement a échoué !", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}