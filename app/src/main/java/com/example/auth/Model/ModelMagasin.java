package com.example.auth.Model;

public class ModelMagasin {
    private String nom;
    private String adresse;
    private String adresseuri;
    private String item_id;
    private String categorie;
    private String photo;

    private ModelMagasin() {
    }

    private ModelMagasin(String nom, String adresse, String item_id, String photo, String categorie,String adresseuri) {
        this.nom = nom;
        this.adresse = adresse;
        this.item_id = item_id;
        this.photo = photo;
        this.categorie = categorie;
        this.adresseuri= adresseuri;
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

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAdresseuri() {
        return adresseuri;
    }

    public void setAdresseuri(String adresseuri) {
        this.adresseuri = adresseuri;
    }
}
