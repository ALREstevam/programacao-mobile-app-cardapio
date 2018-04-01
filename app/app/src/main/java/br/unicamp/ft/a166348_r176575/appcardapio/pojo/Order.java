package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andre on 01/04/2018.
 */

public class Order {
    private Set<OrderItem> orderItems;


    public Order() {
        this.orderItems = new HashSet<>();
    }

    public double getTotalPrice(){
        double sum = 0;

        for(OrderItem item : orderItems){
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public boolean addProduct(Product prod, short amount){
        return false;
    }

    public boolean remove(Product prod){
        return false;
    }

    public boolean addOne(Product prod){
        return false;
    }

    public boolean removeOne(Product prod){
        return false;
    }

    public boolean setProductAmount(Product prod, short amount){
        return false;
    }



}
