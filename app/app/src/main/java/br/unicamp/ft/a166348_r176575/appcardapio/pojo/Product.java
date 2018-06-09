package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import java.io.Serializable;

/**
 * Created by andre on 05/06/2018.
 */

public class Product implements Serializable {
    protected String name;
    protected String description;
    protected String picture;
    protected double price;
    protected long produtctId;

    public Product() {
    }

    public Product(String name, String description, String picture, double price, long produtctId) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.price = price;
        this.produtctId = produtctId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getProdutctId() {
        return produtctId;
    }

    public void setProdutctId(long produtctId) {
        this.produtctId = produtctId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", produtctId=" + produtctId +
                '}';
    }
}
