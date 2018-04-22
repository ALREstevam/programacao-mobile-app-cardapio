/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;
import java.io.Serializable;



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
