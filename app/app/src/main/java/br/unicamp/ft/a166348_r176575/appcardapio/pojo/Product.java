package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import android.support.annotation.NonNull;

/**
 * Created by andre on 01/04/2018.
 */

public class Product implements Comparable<Product>{

    private String name;
    private String description;
    private String picture;
    private ProductGroup group;
    private double price;
    private long produtctId;

    public ProductGroup getGroup() {
        return group;
    }

    public void setGroup(ProductGroup group) {
        this.group = group;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (Double.compare( product.getPrice(), getPrice() ) != 0) return false;
        if (getProdutctId() != product.getProdutctId()) return false;
        if (!getName().equals( product.getName() )) return false;
        if (getDescription() != null ? !getDescription().equals( product.getDescription() ) : product.getDescription() != null)
            return false;
        return getGroup() == product.getGroup();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getGroup().hashCode();
        result = 31 * result + (int) (getProdutctId() ^ (getProdutctId() >>> 32));
        return result;
    }

    @Override
    public int compareTo(@NonNull Product o) {
        return 0;
    }
}

/*
* GRUPO > PREÇO (menor para maior) > NOME
* */