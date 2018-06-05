package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;

/**
 * Created by andre on 01/04/2018.
 */

public class Product implements Comparable<Product>,Parcelable {

    protected String name;
    protected String description;
    protected String picture;
    protected ProductType group;
    protected double price;
    protected long produtctId;

    public Product(String name, String description, String picture, ProductType group, double price, long produtctId) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.group = group;
        this.price = price;
        this.produtctId = produtctId;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product( in );
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public ProductType getGroup() {
        return group;
    }

    public void setGroup(ProductType group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getProdutctId() {
        return produtctId;
    }

    public void setProdutctId(long produtctId) {
        this.produtctId = produtctId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (Double.compare( product.getPrice(), getPrice() ) != 0) return false;
        if (getProdutctId() != product.getProdutctId()) return false;
        if (!getName().equals( product.getName() )) return false;
        if (getDescription() != null ? !getDescription().equals( product.getDescription() ) : product.getDescription() != null)
            return false;
        return getGroup() == product.getGroup();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getGroup().hashCode();
        result = 31 * result + (int) (getProdutctId() ^ (getProdutctId() >>> 32));
        return result;
    }

    @Override
    public int compareTo(@NonNull Product o) {
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
        dest.writeInt( this.group == null ? -1 : this.group.ordinal() );
        dest.writeDouble( this.price );
        dest.writeLong( this.produtctId );
    }

    protected Product(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.picture = in.readString();
        int tmpGroup = in.readInt();
        this.group = tmpGroup == -1 ? null : ProductType.values()[tmpGroup];
        this.price = in.readDouble();
        this.produtctId = in.readLong();
    }

}

/*
* GRUPO > PREÇO (menor para maior) > NOME
* */