package br.unicamp.ft.a166348_r176575.appcardapio.pojo;

/**
 * Created by andre on 01/04/2018.
 */

public enum Sex {
    MALE, FEMALE, OTHER;

    public String getInitial(){
        if(this == Sex.FEMALE){
            return "F";
        }
        else if(this == Sex.MALE) {
            return "M";
        }
        else{
            return "O";
        }
    }

}
