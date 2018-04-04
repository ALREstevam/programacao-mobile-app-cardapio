package br.unicamp.ft.a166348_r176575.appcardapio.sell;

import android.os.Parcel;

import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProductType;


public class SellableProduct extends Product implements Sellable  {
    private int amount;

    public SellableProduct(String name, String description, String picture, ProductType group, double price, long produtctId, int amount) {
        super( name, description, picture, group, price, produtctId );
        this.amount = amount;
    }

    @Override
    public void setAmountInCart(int amount) {
        if(amount >= 0){
            this.amount = amount;
        }
    }

    @Override
    public int getAmountInCart() {
        return this.amount;
    }

    @Override
    public Double getTotalPrice() {
        return this.amount * this.getPrice();
    }

    @Override
    public void addOne() {
        this.amount += 1;
    }

    @Override
    public void removeOne() {
        if (this.amount > 0){
            this.amount -= 1;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel( dest, flags );
        dest.writeInt( this.amount );
        dest.writeString( this.name );
        dest.writeString( this.description );
        dest.writeString( this.picture );
        dest.writeInt( this.group == null ? -1 : this.group.ordinal() );
        dest.writeDouble( this.price );
        dest.writeLong( this.produtctId );
    }

    protected SellableProduct(Parcel in) {
        super( in );
        this.amount = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
        int tmpGroup = in.readInt();
        this.group = tmpGroup == -1 ? null : ProductType.values()[tmpGroup];
        this.price = in.readDouble();
        this.produtctId = in.readLong();
    }

    public static final Creator<SellableProduct> CREATOR = new Creator<SellableProduct>() {
        @Override
        public SellableProduct createFromParcel(Parcel source) {
            return new SellableProduct( source );
        }

        @Override
        public SellableProduct[] newArray(int size) {
            return new SellableProduct[size];
        }
    };
}
