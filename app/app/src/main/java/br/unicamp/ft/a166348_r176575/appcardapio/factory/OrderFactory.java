package br.unicamp.ft.a166348_r176575.appcardapio.factory;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.sell.Menu;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Order;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Sellable;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;

/**
 * Created by andre on 02/04/2018.
 */

public class OrderFactory {

    public Order createOrder(Menu menu){
        List<SellableProduct> lst = new ArrayList<>();

        for(SellableProduct item : menu.getMenuProducts()){
            if(item.getAmountInCart() > 0){
               lst.add( item );
            }
        }
        return new Order(lst);
    }

}
