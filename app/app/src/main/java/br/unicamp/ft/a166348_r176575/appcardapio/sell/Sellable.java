package br.unicamp.ft.a166348_r176575.appcardapio.sell;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by andre on 01/04/2018.
 */

public interface Sellable extends Serializable, Parcelable{
    public void setAmountInCart(int amount);
    public int getAmountInCart();
    public Double getTotalPrice();
    public void addOne();
    public void removeOne();

}
