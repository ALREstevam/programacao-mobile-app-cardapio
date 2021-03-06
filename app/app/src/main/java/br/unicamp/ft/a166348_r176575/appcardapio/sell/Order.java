package br.unicamp.ft.a166348_r176575.appcardapio.sell;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProdStatus;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProductType;


public class Order implements Parcelable {
    private List<SellableProduct> sellables;

    public Order(List<SellableProduct> sellables) {
        this.sellables = sellables;
    }


    public List<SellableProduct> getSellables() {
        return sellables;
    }

    public List<SellableProduct> getSellablesWithStatus(ProdStatus status){
        List<SellableProduct> answer = new ArrayList<>(  );

        for(SellableProduct sellable : this.sellables){
            if(sellable.getStatusEnum() == status){
                answer.add(sellable);
            }
        }
        return answer;
    }

    public List<SellableProduct> getNonSubmitedSellables(){
        return this.getSellablesWithStatus( ProdStatus.NAO_PEDIDO );
    }


    public void setSellables(List<SellableProduct> sellables) {
        this.sellables = sellables;
    }

    public int getQuantity(){
        return this.sellables.size();
    }

    public boolean hasSellable(){
        return this.getQuantity() > 0;
    }

    public double getTotalPrice(){
        double price = 0;
        for(SellableProduct item : this.getSellables()){
            if(item.getStatusEnum() != ProdStatus.PEDIDO_ANTIGO ){
                price += item.getTotalPrice();
            }
        }
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList( this.sellables );
    }

    protected Order(Parcel in) {
        this.sellables = in.createTypedArrayList( SellableProduct.CREATOR );
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order( source );
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
