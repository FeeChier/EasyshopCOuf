package com.example.auth;

public class ModelMagasin {
    private String nom;
    private String adresse;
    private String item_id;

    private ModelMagasin(){}

    private ModelMagasin(String nom, String adresse, String item_id){
        this.nom = nom;
        this.adresse = adresse;
        this.item_id = item_id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}
