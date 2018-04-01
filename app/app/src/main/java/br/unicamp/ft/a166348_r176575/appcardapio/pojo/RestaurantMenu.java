package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import java.util.List;

/**
 * Created by andre on 01/04/2018.
 */

public class RestaurantMenu {
    private List<Product> products;

    public RestaurantMenu(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
