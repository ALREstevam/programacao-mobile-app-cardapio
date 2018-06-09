package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 06/06/2018.
 */

public class RestaurantTable {
    private int chairs;
    private int id;
    private String name;

    public RestaurantTable(int chairs, int id, String name) {
        this.chairs = chairs;
        this.id = id;
        this.name = name;
    }

    public RestaurantTable() {
    }

    public int getChairs() {
        return chairs;
    }

    public void setChairs(int chairs) {
        this.chairs = chairs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "chairs=" + chairs +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
