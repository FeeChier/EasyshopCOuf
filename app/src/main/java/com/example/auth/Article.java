package com.example.auth;


public class Article {
    private String name;
    private String description;
    private String price;
    private String itemId;
    private String photo;
    private String description_complete;
    private int qtt;

    public Article(){}

    public Article(String item, String name, String description, String price, String photo, String description_complete, int qtt){
        this.description = description;
        this.price = price;
        this.name = name;
        this.itemId = item;
        this.photo = photo;
        this.description_complete = description_complete;
        this.qtt = qtt;
    }


    public String getitemId() {
        return itemId;
    }

    public void setitemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription_complete() {
        return description_complete;
    }

    public void setDescription_complete(String description_complete) {
        this.description_complete = description_complete;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

}
