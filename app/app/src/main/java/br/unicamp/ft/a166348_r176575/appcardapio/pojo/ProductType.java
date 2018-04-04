package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

import java.io.Serializable;

/**
 * Created by andre on 01/04/2018.
 */

public enum ProductType implements Serializable{
    DOCE(2),
    SALGADO(1),
    BEBIDA(4),
    SOBREMESA(3),
    BEBIDA_ALCOOLICA(5);

    int groupNumber;
    ProductType(int gnum){
        this.groupNumber = gnum;
    }

    public int getGroupNumber() {
        return groupNumber;
    }


}
