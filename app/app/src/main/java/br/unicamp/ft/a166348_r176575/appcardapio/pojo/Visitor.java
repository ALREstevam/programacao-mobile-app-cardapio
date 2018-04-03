package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andre on 01/04/2018.
 */

public class Visitor implements Parcelable {

    private String name;
    private short peopleOnTable;
    private Sex sex;
    private String tableName;
    private long id;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPeopleOnTable() {
        return peopleOnTable;
    }

    public void setPeopleOnTable(short peopleOnTable) {
        this.peopleOnTable = peopleOnTable;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( this.name );
        dest.writeInt( this.peopleOnTable );
        dest.writeInt( this.sex == null ? -1 : this.sex.ordinal() );
        dest.writeString( this.tableName );
        dest.writeLong( this.id );
    }

    public Visitor() {
    }

    protected Visitor(Parcel in) {
        this.name = in.readString();
        this.peopleOnTable = (short) in.readInt();
        int tmpSex = in.readInt();
        this.sex = tmpSex == -1 ? null : Sex.values()[tmpSex];
        this.tableName = in.readString();
        this.id = in.readLong();
    }

    public static final Parcelable.Creator<Visitor> CREATOR = new Parcelable.Creator<Visitor>() {
        @Override
        public Visitor createFromParcel(Parcel source) {
            return new Visitor( source );
        }

        @Override
        public Visitor[] newArray(int size) {
            return new Visitor[size];
        }
    };
}
