package com.example.auth;

public class ListModel {
    private String nom;
    private String description;
    private String prix;

    public ListModel(){}

    public ListModel(String nom, String description, String prix){
        this.nom = nom;
        this.description = description;
        this.prix= prix;
    }

    public String getNom() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrice(String prix) {
        this.prix = prix;
    }
}
