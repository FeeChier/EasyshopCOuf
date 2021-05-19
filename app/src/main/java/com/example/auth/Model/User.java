package com.example.auth.Model;

public class User {
    public String prénom, nom, email;

    public User(){

    }
    public User(String prénom, String nom, String email){
        this.prénom= prénom;
        this.nom = nom;
        this.email = email;
    }

    public String getFirstName(){return prénom;}
    public String getLastName() {return nom;}
    public String getEmail(){return email;}

}
