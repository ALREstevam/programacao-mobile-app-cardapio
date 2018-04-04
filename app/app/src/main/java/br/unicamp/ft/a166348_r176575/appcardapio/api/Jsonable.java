package br.unicamp.ft.a166348_r176575.appcardapio.api;

/**
 * Created by andre on 02/04/2018.
 */

public interface Jsonable<Elem> {
    public String asJson();
    public Elem asObject(String json);
}
