package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 01/04/2018.
 */

public class OrderItem {
    private Product product;
    private short amount;

    public double getTotalPrice(){
        return product.getPrice() * amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public short getAmount() {
        return amount;
    }

    public void setAmount(short amount) {
        this.amount = amount;
    }
}
