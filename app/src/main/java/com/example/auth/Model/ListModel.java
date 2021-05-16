package com.example.auth.Model;

public class ListModel {
    private String nom;
    private String description;
    private String prix;
    private String article_id;
    private String qtt;
    private String total;
    public ListModel(){}

    public ListModel(String nom, String description, String prix, String article_id, String qtt, String total){
        this.nom = nom;
        this.description = description;
        this.prix= prix;
        this.article_id = article_id;
        this.qtt = qtt;
        this.total = total;
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

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getQtt() {
        return qtt;
    }

    public void setQtt(String qtt) {
        this.qtt = qtt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
