package br.unicamp.ft.a166348_r176575.appcardapio.sell;

import android.os.Parcel;

import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProdStatus;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProdStatusHelp;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProductSendable;


public class SellableProduct extends ProductSendable implements Sellable  {
    private int amount;
    private String state;
    private ProdStatus statusEnum;
    private long localId;

    public SellableProduct(Parcel in, String state) {
        super( in );
        this.state = state;
        this.statusEnum = ProdStatusHelp.fromText( state );
    }

    public SellableProduct(String name, String description, String picture, double price, long produtctId, int amount, String state) {
        super( name, description, picture, price, produtctId );
        this.amount = amount;
        this.state = state;
        this.statusEnum = ProdStatusHelp.fromText( state );
    }

    public SellableProduct(Parcel in, int amount, String state, ProdStatus statusEnum, long localId) {
        super( in );
        this.amount = amount;
        this.state = state;
        this.statusEnum = statusEnum;
        this.localId = localId;
    }



    public SellableProduct(String name, String description, String picture, double price, long produtctId, int amount, String state, ProdStatus statusEnum, long localId) {
        super( name, description, picture, price, produtctId );
        this.amount = amount;
        this.state = state;
        this.statusEnum = statusEnum;
        this.localId = localId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SellableProduct(Parcel in, int amount, String state) {
        super( in );
        this.amount = amount;
        this.state = state;
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
        dest.writeDouble( this.price );
        dest.writeLong( this.produtctId );
    }

    protected SellableProduct(Parcel in) {
        super( in );
        this.amount = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
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

    public ProdStatus getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(ProdStatus statusEnum) {
        this.statusEnum = statusEnum;
        this.state = statusEnum.getStatusAsText();
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    @Override
    public String toString() {
        return "SellableProduct{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", produtctId=" + produtctId +
                ", amount=" + amount +
                ", state='" + state + '\'' +
                ", statusEnum=" + statusEnum +
                ", localId=" + localId +
                '}';
    }
}
