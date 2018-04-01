package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 01/04/2018.
 */

public class Visitor {

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
}
