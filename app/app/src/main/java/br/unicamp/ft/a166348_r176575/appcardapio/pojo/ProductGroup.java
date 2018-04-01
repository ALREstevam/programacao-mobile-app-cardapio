package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 01/04/2018.
 */

public enum ProductGroup {
    DOCE(2),
    SALGADO(1),
    BEBIDA(4),
    SOBREMESA(3),
    BEBIDA_ALCOOLICA(5);

    int groupNumber;
    ProductGroup(int gnum){
        this.groupNumber = gnum;
    }

    public int getGroupNumber() {
        return groupNumber;
    }
}
