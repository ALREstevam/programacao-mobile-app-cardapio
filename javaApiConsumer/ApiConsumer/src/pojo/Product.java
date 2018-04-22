/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author andre
 */
public class Product {
    /*
    "descr": "Descricao produto1", 
    "id": 1, 
    "name": "Produto1", 
    "price": "10.00", 
    "prodpic": "1.png", 
    "type": "DOCE"
    */
    
    private String name;
    private String description;
    private String picture;
    private ProductType type;
    private double price;
    private long produtctId;

    public Product() {
    }

    public Product(String name, String description, String picture, ProductType type, double price, long produtctId) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.type = type;
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

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
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
        return "Product{" + "name=" + name + ", description=" + description + ", picture=" + picture + ", type=" + type + ", price=" + price + ", produtctId=" + produtctId + '}';
    }
    
    

   
    
    
}
