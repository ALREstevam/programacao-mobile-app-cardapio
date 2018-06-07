package br.unicamp.ft.a166348_r176575.appcardapio.sell;

import java.util.List;

/**
 * Created by andre on 02/04/2018.
 */

public class Menu {
    private List<SellableProduct> menuProducts;

    public Menu(List<SellableProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public Menu() {
    }

    public void setMenuProducts(List<SellableProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<SellableProduct> getMenuProducts() {
        return menuProducts;
    }
}
