package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by andre on 01/04/2018.
 */

public class ProductSendable extends Product implements Comparable<ProductSendable>, Parcelable {


    public static Creator<ProductSendable> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<ProductSendable> CREATOR = new Creator<ProductSendable>() {
        @Override
        public ProductSendable createFromParcel(Parcel in) {
            return new ProductSendable( in );
        }

        @Override
        public ProductSendable[] newArray(int size) {
            return new ProductSendable[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductSendable)) return false;

        ProductSendable product = (ProductSendable) o;

        if (Double.compare( product.getPrice(), getPrice() ) != 0) return false;
        if (getProdutctId() != product.getProdutctId()) return false;
        if (!getName().equals( product.getName() )) return false;
        if (getDescription() != null ? !getDescription().equals( product.getDescription() ) : product.getDescription() != null) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (int) (getProdutctId() ^ (getProdutctId() >>> 32));
        return result;
    }

    @Override
    public int compareTo(@NonNull ProductSendable o) {
        return 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( this.name );
        dest.writeString( this.description );
        dest.writeString( this.picture );
        dest.writeDouble( this.price );
        dest.writeLong( this.produtctId );
    }

    protected ProductSendable(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
        this.price = in.readDouble();
        this.produtctId = in.readLong();
    }

    public ProductSendable(String name, String description, String picture, double price, long produtctId) {
        super( name, description, picture, price, produtctId );
    }

    @Override
    public String toString() {
        return "ProductSendable{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", produtctId=" + produtctId +
                '}';
    }
}

