package com.example.auth.Model;

public class MagasinPremiumModel {

    private String nom;
    private String adresse;
    private String item_id;
    private String categorie;
    private String photo;

    private MagasinPremiumModel(){}

    private MagasinPremiumModel(String nom, String adresse, String item_id, String categorie,String photo){
        this.nom =nom;
        this.adresse = adresse;
        this.item_id = item_id;
        this.categorie = categorie;
        this.photo = photo;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
